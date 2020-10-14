package com.alexlepadatu.githubusers.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.base.BaseFragment
import com.alexlepadatu.githubusers.search.SearchFragment
import com.alexlepadatu.githubusers.users.UsersFragment

class MainFragment  : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchFragment = childFragmentManager.findFragmentById(R.id.searchFragment) as SearchFragment
        searchFragment.searchStringListener = {
            val forecastFragment = childFragmentManager.findFragmentById(R.id.usersFragment) as UsersFragment
            forecastFragment.setSearchString(it)
        }
    }
}