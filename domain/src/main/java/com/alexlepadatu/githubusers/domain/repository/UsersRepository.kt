package com.alexlepadatu.githubusers.domain.repository

import androidx.paging.PagedList
import com.alexlepadatu.githubusers.domain.models.User
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

interface UsersRepository {
    fun getUsers(searchString: String): Flowable<PagedList<User>>

    fun networkState(): Flowable<NetworkState>

    fun disposable(): Disposable?
}