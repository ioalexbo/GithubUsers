package com.alexlepadatu.githubusers.domain.repository

import androidx.paging.PagedList
import com.alexlepadatu.githubusers.domain.models.User
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface UsersRepository {
    fun getUsers(searchString: String): Observable<PagedList<User>>

    fun disposable(): Disposable?
}