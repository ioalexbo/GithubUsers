package com.alexlepadatu.githubusers.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.alexlepadatu.githubusers.R
import com.alexlepadatu.githubusers.base.BaseFragment
import com.alexlepadatu.meisterlabs.util.DelayedActionHandler

class SearchFragment : BaseFragment(), SearchView.OnQueryTextListener {
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View =
        inflater.inflate(R.layout.fragment_search, container, false)

    lateinit var searchStringListener: (searchString: String) -> Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
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
        searchString?.let {
            viewModel.searchString = it
            searchStringListener(it)
        }
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

            searchView.setQuery(viewModel.searchString, false)
        }
    }

    private fun showSearchInfo() {
        viewModel.searchViewOpen = false
    }

    private fun hideSearchInfo() {
        viewModel.searchViewOpen = true
    }
}