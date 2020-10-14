package com.alexlepadatu.githubusers.search

import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    var searchViewOpen = false
    var searchString: String = ""
}