package com.alexlepadatu.githubusers.users

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.data.api.GitApi
import com.alexlepadatu.githubusers.data.api.GitApiDataSource
import com.alexlepadatu.githubusers.data.api.GitService
import com.alexlepadatu.githubusers.data.database.AppDatabase
import com.alexlepadatu.githubusers.data.database.UsersDbDataSource
import com.alexlepadatu.githubusers.data.repository.UsersRepositoryImpl
import com.alexlepadatu.githubusers.domain.useCase.GitUsersUseCaseImpl
import com.alexlepadatu.meisterlabs.util.DelayedActionHandler
import com.alexlepadatu.trendingrepos.domain.common.RuntimeSchedulerProvider
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment(), SearchView.OnQueryTextListener {
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

    private val adapter = UsersPagedListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View =
        inflater.inflate(R.layout.fragment_users, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        rvData.adapter = adapter

        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)

        val searchItem = menu.findItem(R.id.searchBar)
        val searchView = searchItem.actionView as SearchView

        setupSearchView(searchItem, searchView)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    private val delayedSearchHandler = DelayedActionHandler<String?>(400L) { searchString ->
        viewModel.searchString.value = searchString
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        delayedSearchHandler.setItem(newText)

        return true
    }

    private fun setupSearchView(searchItem: MenuItem, searchView: SearchView) {
        searchView.queryHint = searchItem.title
        searchView.setOnQueryTextListener(this)
        searchView.maxWidth = android.R.attr.width
        searchView.imeOptions = searchView.imeOptions or EditorInfo.IME_FLAG_NO_EXTRACT_UI

        searchView.setOnSearchClickListener {
            hideSearchInfo()
        }

        searchView.setOnCloseListener {
            showSearchInfo()
            false
        }

        if (viewModel.searchViewOpen) {     // restore searchView's state
            searchView.isIconified = false

            searchView.setQuery(viewModel.searchString.value, false)
        }
    }

    private fun showSearchInfo() {
        viewModel.searchViewOpen = false
//        viewSearchInfo.visibility = View.VISIBLE
    }

    private fun hideSearchInfo() {
        viewModel.searchViewOpen = true
//        viewSearchInfo.visibility = View.GONE
    }
}