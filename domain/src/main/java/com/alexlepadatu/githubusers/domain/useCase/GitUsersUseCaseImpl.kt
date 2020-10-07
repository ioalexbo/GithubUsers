package com.alexlepadatu.githubusers.domain.useCase

import androidx.paging.PagedList
import com.alexlepadatu.githubusers.domain.models.User
import com.alexlepadatu.githubusers.domain.repository.UsersRepository
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

class GitUsersUseCaseImpl (private val repo: UsersRepository) : GitUsersUseCase {

    override fun getUsers(searchString: String): Observable<PagedList<User>> {
        return repo.getUsers(searchString)
    }

    override fun disposable(): Disposable? = repo.disposable()
}