package com.alexlepadatu.githubusers.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexlepadatu.githubusers.domain.useCase.GitUsersUseCase

class UsersViewModelFactory (private val useCase: GitUsersUseCase) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(useCase) as T
    }
}