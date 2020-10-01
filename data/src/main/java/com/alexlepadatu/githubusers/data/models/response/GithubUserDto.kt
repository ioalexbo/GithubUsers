package com.alexlepadatu.githubusers.data.models.response

import com.google.gson.annotations.SerializedName

data class GithubUserDto (
    val id: Int,
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val url: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("followers_url") val followersUrl: String,
    @SerializedName("organizations_url") val organizationsUrl: String,
    @SerializedName("repos_url") val reposUrl: String,
    @SerializedName("events_url") val eventsUrl: String,
    @SerializedName("received_events_url") val receivedEventsUrl: String
)