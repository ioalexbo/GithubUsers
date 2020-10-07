package com.alexlepadatu.githubusers.data.api

import com.alexlepadatu.githubusers.data.models.response.SearchResponseDto
import io.reactivex.Single

class GitApiDataSource (private val api: GitApi) {

    fun fetchRepos(filter: QueryFilter, pageNo: Int, itemsPerPage: Int = GitApi.DEFAULT_ITEMS_PER_PAGE): Single<SearchResponseDto> =
        api.fetchRepos(filter, pageNo, itemsPerPage)
}