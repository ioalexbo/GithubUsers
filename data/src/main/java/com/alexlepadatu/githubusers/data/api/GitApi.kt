package com.alexlepadatu.githubusers.data.api

import com.alexlepadatu.githubusers.data.models.response.SearchResponseDto
import io.reactivex.Single
import retrofit2.http.GET

interface GitApi {
    @GET("search/users")
    fun fetchRepos(filter: QueryFilter, pagingInfo: PagingInfo?): Single<SearchResponseDto>
}

data class QueryFilter(private val login: String) {
    override fun toString(): String {
        return "q=$login+in:login+repos:>100&type=Users"
    }
}

data class PagingInfo(
    private val pageNo: Int,
    private val itemsPerPage: Int = DEFAULT_ITEMS_PER_PAGE
) {
    companion object {
        private const val DEFAULT_ITEMS_PER_PAGE = 30
    }

    override fun toString(): String {
        return "page=$pageNo&per_page=itemsPerPage"
    }
}