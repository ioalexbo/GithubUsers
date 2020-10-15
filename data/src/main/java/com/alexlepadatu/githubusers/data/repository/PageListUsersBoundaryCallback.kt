package com.alexlepadatu.githubusers.data.repository

import android.util.Log
import androidx.paging.PagedList
import com.alexlepadatu.githubusers.data.api.GitApi
import com.alexlepadatu.githubusers.data.api.GitApiDataSource
import com.alexlepadatu.githubusers.data.api.QueryFilter
import com.alexlepadatu.githubusers.data.database.UsersDbDataSource
import com.alexlepadatu.githubusers.data.mappers.mapToEntity
import com.alexlepadatu.githubusers.data.mappers.toEntity
import com.alexlepadatu.githubusers.data.models.entity.SearchResponseEntity
import com.alexlepadatu.githubusers.data.models.response.SearchResponseDto
import com.alexlepadatu.githubusers.domain.models.User
import com.alexlepadatu.githubusers.domain.repository.NetworkState
import com.alexlepadatu.trendingrepos.domain.common.SchedulerProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.atomic.AtomicBoolean

class PageListUsersBoundaryCallback (
    private val executors: SchedulerProvider,
    private val usersRemoteDataSource: GitApiDataSource,
    private val usersDbDataSource: UsersDbDataSource) : PagedList.BoundaryCallback<User>() {

    private var searchString: String = ""
    private var publisherSearchString = ReplaySubject.create<String>()

    private val networkState = PublishSubject.create<NetworkState>()

    private var isRequestRunning = AtomicBoolean(false)
    private var requestedPage = 1
    var disposable: Disposable? = null

    fun setSearchString(searchString: String) {
        if (this.searchString != searchString) {
            this.searchString = searchString
            requestedPage = 1

            publisherSearchString.onNext(searchString)
        }
    }

    fun networkStateObservable(): Flowable<NetworkState> {
        return networkState.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun onZeroItemsLoaded() {
        fetchAndStoreUsers()
    }

    override fun onItemAtEndLoaded(itemAtEnd: User) {
        fetchAndStoreUsers()
    }

    private val emptyObservable: Observable<SearchResponseDto> by lazy {
        Observable.empty<SearchResponseDto>()
    }

    private val doOnNoFetch : () -> Observable<SearchResponseDto> = {
        isRequestRunning.set(false)
        emptyObservable
    }

    private fun fetchAndStoreUsers() {
        if (isRequestRunning.get()) return

        disposable?.dispose()

        disposable = publisherSearchString
            .filter { it == searchString }
            .distinctUntilChanged()
                .subscribeOn(executors.io())
                .observeOn(executors.io())
                .flatMap { string ->
                    if (string.length <= 2) {
                        isRequestRunning.set(false)
                        return@flatMap emptyObservable
                    }

                    if (!usersDbDataSource.canStillGetUsersForSearchString(string)) {
                        isRequestRunning.set(false)
                        return@flatMap emptyObservable
                    }

                    networkState.onNext(NetworkState.Loading())

                    return@flatMap usersRemoteDataSource.fetchRepos(QueryFilter(string), requestedPage, GitApi.DEFAULT_ITEMS_PER_PAGE)
                        .doOnSuccess {
                            val searchResponseEntity: SearchResponseEntity = it.toEntity(string)
                            val users = it.items.mapToEntity()

                            usersDbDataSource.persistUsersForSearchString(searchResponseEntity, users)

                            requestedPage ++

                            networkState.onNext(NetworkState.Success())
                        }
                        .toObservable()
                }
                .take(1)
                .doOnSubscribe {
                    isRequestRunning.set(true)
                }
                .doFinally {
                    isRequestRunning.set(false)
                }
                .subscribe({
    //                Log.e("PageListUsersBndryClbk", "Movies Completed: $searchString")
                },
                    {
                        networkState.onNext(NetworkState.Failed(it.message))
                        Log.e("PageListUsersBndryClbk", "error: $it")
                    }
                )
    }
}