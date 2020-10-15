package com.alexlepadatu.githubusers.users

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.alexlepadatu.githubusers.domain.models.User
import com.alexlepadatu.githubusers.domain.repository.NetworkState
import com.alexlepadatu.githubusers.domain.useCase.GitUsersUseCase
import io.reactivex.disposables.CompositeDisposable

class UsersViewModel (private val useCase: GitUsersUseCase): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val searchString = MutableLiveData<String>()

    private val usersBacking = MediatorLiveData<PagedList<User>>().also { mediator ->
        mediator.addSource(searchString) {
            getUsers()
        }
    }

    val users: LiveData<PagedList<User>> = Transformations.map(usersBacking) {
        it
    }

    val networkState : LiveData<NetworkState> = MutableLiveData<NetworkState>().also { mutable ->
        val disposable = useCase.networkState()
            .subscribe({
                mutable.postValue(it)
            }, {
                
            })

        compositeDisposable.add(disposable)
    }

    private fun getUsers() {
        val disposable = useCase.getUsers(searchString.value!!)
            .subscribe({ usersBacking.value = it },
                {  })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        useCase.disposable()?.run { compositeDisposable.add(this) }
        compositeDisposable.dispose()
    }
}