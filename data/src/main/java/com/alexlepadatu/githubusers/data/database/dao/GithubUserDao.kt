package com.alexlepadatu.githubusers.data.database.dao

import androidx.room.Dao
import com.alexlepadatu.githubusers.data.models.entity.GithubUserEntity
import com.alexlepadatu.trendingrepos.data.base.data.BaseDao

@Dao
abstract class GithubUserDao: BaseDao<GithubUserEntity>