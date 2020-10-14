package com.alexlepadatu.githubusers.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.base.BaseFragment
import com.alexlepadatu.githubusers.data.api.GitApi
import com.alexlepadatu.githubusers.data.api.GitApiDataSource
import com.alexlepadatu.githubusers.data.api.GitService
import com.alexlepadatu.githubusers.data.database.AppDatabase
import com.alexlepadatu.githubusers.data.database.UsersDbDataSource
import com.alexlepadatu.githubusers.data.repository.UsersRepositoryImpl
import com.alexlepadatu.githubusers.detail.UserDetailFragment
import com.alexlepadatu.githubusers.domain.useCase.GitUsersUseCaseImpl
import com.alexlepadatu.trendingrepos.domain.common.RuntimeSchedulerProvider
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : BaseFragment() {

    private val viewModel: UsersViewModel by viewModels {
        val executors = RuntimeSchedulerProvider()
        val db = AppDatabase.getInstance(requireContext())

        return@viewModels UsersViewModelFactory(
            GitUsersUseCaseImpl(
                UsersRepositoryImpl(executors,
                    GitApiDataSource(GitService.getRetrofit().create(GitApi::class.java)),
                    UsersDbDataSource(db.githubUserDao(), db.searchResponseDao(), db)
                )
            )
        )
    }

    private val adapter = UsersPagedListAdapter( {
//        viewModel.retry()
    },
        { user ->
            val detailFragment = UserDetailFragment.getInstance(user)
            addFragment(detailFragment)
        })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View =
        inflater.inflate(R.layout.fragment_users, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvData.adapter = adapter

        initObservables()
    }

    private fun initObservables() {
        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })
    }

    fun setSearchString(searchString: String) {
        viewModel.searchString.value = searchString
    }
}