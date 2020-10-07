package com.alexlepadatu.githubusers.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alexlepadatu.githubusers.domain.models.User
import com.alexlepadatu.githubusers.domain.useCase.GitUsersUseCase
import io.reactivex.disposables.CompositeDisposable

class UsersViewModel (private val useCase: GitUsersUseCase): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    var searchViewOpen = false

    val searchString = MutableLiveData<String>()

    val users = MediatorLiveData<PagedList<User>>().also {
        it.addSource(searchString) {
            if (it.length > 1) {
                getUsers()
            }
        }
    }

    private fun getUsers() {
        val disposable = useCase.getUsers(searchString.value!!)
            .subscribe({ users.value = it },
                { Log.e("UsersViewModel", "error: $it") })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        useCase.disposable()?.run { compositeDisposable.add(this) }
        compositeDisposable.dispose()
    }
}