package com.alexlepadatu.githubusers.data.database

import androidx.paging.DataSource
import com.alexlepadatu.githubusers.data.database.dao.GithubUserDao
import com.alexlepadatu.githubusers.data.database.dao.SearchResponseDao
import com.alexlepadatu.githubusers.data.mappers.toDomain
import com.alexlepadatu.githubusers.data.models.entity.GithubUserEntity
import com.alexlepadatu.githubusers.data.models.entity.SearchResponseEntity
import com.alexlepadatu.githubusers.domain.models.User

class UsersDbDataSource (private val daoUsers: GithubUserDao,
                         private val daoSearch: SearchResponseDao,
                         private val db: AppDatabase
) {
    fun persistUsersForSearchString(searchResponseEntity: SearchResponseEntity, users: List<GithubUserEntity>) {
        db.runInTransaction {
            val existingEntity = daoSearch.getEntity(searchResponseEntity.searchString)

            val dbEntity : SearchResponseEntity = if (existingEntity != null) {
                existingEntity
            }
            else {
                daoSearch.insert(searchResponseEntity)
                daoSearch.getEntity(searchResponseEntity.searchString)!!
            }

            val reached = daoUsers.getNoOfUsersForSearchString(searchResponseEntity.searchString)

            users.mapIndexed { index, githubUserEntity ->
                githubUserEntity.searchQueryInfoId = dbEntity.id
                githubUserEntity.indexInSearch = reached + 1 + index
            }

            daoUsers.insertAll(*users.toTypedArray())
        }
    }

    fun getUsersForSearchString(searchString: String): DataSource.Factory<Int, User> =
        daoUsers.getUsersForSearchString(searchString)
            .map { it.toDomain() }
}