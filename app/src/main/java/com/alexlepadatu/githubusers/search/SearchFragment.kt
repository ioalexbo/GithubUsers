package com.alexlepadatu.githubusers.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.base.BaseFragment

class SearchFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFilter()
    }
// TODO USE fragment
    fun setupFilter() {
    }
}