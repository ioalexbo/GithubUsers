package com.alexlepadatu.githubusers.data.api

import com.alexlepadatu.githubusers.data.models.response.SearchResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitApi {
    @GET("search/users")
    fun fetchRepos(@Query(value = "q", encoded = true) filter: QueryFilter,
                   @Query("page") pageNo: Int = 1,
                   @Query("per_page") itemsPerPage: Int = DEFAULT_ITEMS_PER_PAGE
    ): Single<SearchResponseDto>

    companion object {
        const val DEFAULT_ITEMS_PER_PAGE = 30
    }
}

// used add additional filters
data class QueryFilter(private val login: String) {
    override fun toString(): String {
        return "$login+in:login+repos:>100"     //&type=Users
    }
}