package com.alexlepadatu.githubusers.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexlepadatu.githubusers.data.database.dao.GithubUserDao
import com.alexlepadatu.githubusers.data.database.dao.SearchResponseDao
import com.alexlepadatu.githubusers.data.models.entity.GithubUserEntity
import com.alexlepadatu.githubusers.data.models.entity.SearchResponseEntity

@Database(entities = [SearchResponseEntity::class, GithubUserEntity::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchResponseDao(): SearchResponseDao

    abstract fun githubUserDao(): GithubUserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        private const val DATABASE_NAME = "github-users-db"

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}