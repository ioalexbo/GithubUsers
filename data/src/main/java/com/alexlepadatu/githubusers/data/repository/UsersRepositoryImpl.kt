package com.alexlepadatu.githubusers.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.alexlepadatu.githubusers.data.api.GitApiDataSource
import com.alexlepadatu.githubusers.data.database.UsersDbDataSource
import com.alexlepadatu.githubusers.domain.models.User
import com.alexlepadatu.githubusers.domain.repository.NetworkState
import com.alexlepadatu.githubusers.domain.repository.UsersRepository
import com.alexlepadatu.trendingrepos.domain.common.SchedulerProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class UsersRepositoryImpl (
    executors: SchedulerProvider,
    apiRemoteDataSource: GitApiDataSource,
    private val usersDbDataSource: UsersDbDataSource) : UsersRepository {

    private val usersBoundaryCallback = PageListUsersBoundaryCallback(executors,
        apiRemoteDataSource,
        usersDbDataSource)

    override fun getUsers(searchString: String): Flowable<PagedList<User>> {
        usersBoundaryCallback.setSearchString(searchString)

        return RxPagedListBuilder(usersDbDataSource.getUsersForSearchString(searchString), 20)
            .setBoundaryCallback(usersBoundaryCallback)
            .buildFlowable(BackpressureStrategy.BUFFER)
    }

    override fun networkState(): Flowable<NetworkState> = usersBoundaryCallback.networkStateObservable()

    override fun disposable() = usersBoundaryCallback.disposable
}