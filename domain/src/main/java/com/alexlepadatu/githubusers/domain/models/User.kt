package com.alexlepadatu.githubusers.domain.models

data class User(
    val indexInSearch: Int,
    val searchQueryInfoId: Int,
    val login: String,
    val avatarUrl: String,
    val url: String,
    val htmlUrl: String,
    val followersUrl: String,
    val organizationsUrl: String,
    val reposUrl: String,
    val eventsUrl: String,
    val receivedEventsUrl: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (login != other.login) return false

        return true
    }

    override fun hashCode(): Int {
        return login.hashCode()
    }
}