package com.alexlepadatu.githubusers.data.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.alexlepadatu.githubusers.data.models.entity.GithubUserEntity
import com.alexlepadatu.trendingrepos.data.base.data.BaseDao

@Dao
abstract class GithubUserDao: BaseDao<GithubUserEntity> {
    @Query("""
        SELECT * FROM github_users 
        WHERE searchQueryInfoId = (SELECT id FROM search_responses WHERE UPPER(searchString) = UPPER(:searchString)) 
        ORDER BY indexInSearch ASC
    """)
    abstract fun getUsersForSearchString(searchString: String): DataSource.Factory<Int, GithubUserEntity>

    @Query("""
        SELECT COUNT(*) as noUsers FROM github_users 
        WHERE searchQueryInfoId = (SELECT id FROM search_responses WHERE UPPER(searchString) = UPPER(:searchString))
    """)
    abstract fun getNoOfUsersForSearchString(searchString: String): Int
}