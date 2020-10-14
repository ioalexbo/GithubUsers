package com.alexlepadatu.githubusers.domain.useCase

import androidx.paging.PagedList
import com.alexlepadatu.githubusers.domain.models.User
import com.alexlepadatu.githubusers.domain.repository.NetworkState
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

interface GitUsersUseCase {
    fun getUsers(searchString: String): Flowable<PagedList<User>>

    fun networkState(): Flowable<NetworkState>

    fun disposable(): Disposable?
}