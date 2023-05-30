package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.StarredRepositoryDao
import com.example.data.database.entity.StarredRepositoryEntity


@Database(entities = [StarredRepositoryEntity::class], version = 1)
abstract class StarredRepositoryDatabase : RoomDatabase() {
    abstract fun repositoryDao(): StarredRepositoryDao

}