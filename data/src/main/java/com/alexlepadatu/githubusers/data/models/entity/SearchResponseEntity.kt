package com.alexlepadatu.githubusers.data.models.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "search_responses",
    indices = [Index(value = ["searchString"], unique = true)])
data class SearchResponseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val totalCount: Int,
    val incompleteResults: Boolean,
    val searchString: String,
    val timestamp: Long
)