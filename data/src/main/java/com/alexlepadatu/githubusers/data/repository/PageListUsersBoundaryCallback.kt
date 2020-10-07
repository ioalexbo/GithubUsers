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
import com.alexlepadatu.githubusers.domain.models.User
import com.alexlepadatu.trendingrepos.domain.common.SchedulerProvider
import io.reactivex.disposables.Disposable

class PageListUsersBoundaryCallback (
    private val executors: SchedulerProvider,
    private val usersRemoteDataSource: GitApiDataSource,
    private val usersDbDataSource: UsersDbDataSource) : PagedList.BoundaryCallback<User>() {

    private var searchString: String = ""

    private var isRequestRunning = false
    private var requestedPage = 1
    var disposable: Disposable? = null

    fun setSearchString(searchString: String) {
        if (this.searchString != searchString) {
            this.searchString = searchString
            requestedPage = 1
        }
    }

    override fun onZeroItemsLoaded() {
        fetchAndStoreUsers()
    }

    override fun onItemAtEndLoaded(itemAtEnd: User) {
        fetchAndStoreUsers()
    }

    private fun fetchAndStoreUsers() {
        if (isRequestRunning) return
        isRequestRunning = true

        // TODO check if still need to try to fetch

        disposable = usersRemoteDataSource.fetchRepos(QueryFilter(searchString), requestedPage, GitApi.DEFAULT_ITEMS_PER_PAGE)
            .doOnSuccess {
                val searchResponseEntity: SearchResponseEntity = it.toEntity(searchString)
                val users = it.items.mapToEntity()

                usersDbDataSource.persistUsersForSearchString(searchResponseEntity, users)

                requestedPage ++
            }
            .subscribeOn(executors.io())
            .observeOn(executors.io())
            .ignoreElement()
            .doFinally { isRequestRunning = false }
            .subscribe({ Log.e("PageListUsersBoundaryCallback", "Movies Completed") }, { it.printStackTrace() })
    }
}