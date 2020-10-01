package com.alexlepadatu.githubusers.data.models.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "github_users",
    indices = [Index(value = ["searchQueryInfoId"])],
    foreignKeys = [ForeignKey(
        entity = SearchResponseEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("searchQueryInfoId"),
        onDelete = ForeignKey.RESTRICT
    )]
)
data class GithubUserEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
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
)