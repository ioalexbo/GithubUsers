package com.alexlepadatu.githubusers.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.alexlepadatu.githubusers.data.models.entity.SearchResponseEntity
import com.alexlepadatu.trendingrepos.data.base.data.BaseDao

@Dao
abstract class SearchResponseDao : BaseDao<SearchResponseEntity> {

    @Query("""
        SELECT * FROM search_responses
        WHERE UPPER(searchString) = UPPER(:searchString)
    """)
    abstract fun getEntity(searchString: String): SearchResponseEntity?
}