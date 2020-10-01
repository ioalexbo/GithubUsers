package com.alexlepadatu.githubusers.data.mappers

import com.alexlepadatu.githubusers.data.models.entity.GithubUserEntity
import com.alexlepadatu.githubusers.data.models.entity.SearchResponseEntity
import com.alexlepadatu.githubusers.data.models.response.GithubUserDto
import com.alexlepadatu.githubusers.data.models.response.SearchResponseDto
import java.util.*

internal fun SearchResponseDto.toEntity(searchString: String): SearchResponseEntity {
    return SearchResponseEntity(0,
        this.totalCount,
        this.incompleteResults,
        searchString,
        Date().time
    )
}

internal fun GithubUserDto.toEntity(index: Int, searchResponseId: Int): GithubUserEntity {
    return GithubUserEntity(0,
        index,
        searchResponseId,
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

internal fun List<GithubUserDto>.mapToEntity(lastIndex: Int, searchResponseId: Int)
        : List<GithubUserEntity> {
    return mapIndexed { index, githubUserDto ->
        githubUserDto.toEntity(lastIndex + 1 + index,
            searchResponseId)
    }
}