package com.alexlepadatu.githubusers.data.models.response

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<GithubUserDto>)