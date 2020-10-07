package com.alexlepadatu.githubusers.data.mappers

import com.alexlepadatu.githubusers.data.models.entity.GithubUserEntity
import com.alexlepadatu.githubusers.domain.models.User

internal fun GithubUserEntity.toDomain(): User {
    return User(this.indexInSearch,
        this.searchQueryInfoId,
        this.login,
        this.avatarUrl,
        this.url,
        this.htmlUrl,
        this.followersUrl,
        this.organizationsUrl,
        this.reposUrl,
        this.eventsUrl,
        this.receivedEventsUrl
    )
}