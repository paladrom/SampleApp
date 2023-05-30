package com.example.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Volatile
    private var INSTANCE: com.example.data.database.StarredRepositoryDatabase? = null

    @Provides
    @Singleton
    fun providesStarredRepositoryDatabase(@ApplicationContext context: Context): com.example.data.database.StarredRepositoryDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context,
                com.example.data.database.StarredRepositoryDatabase::class.java,
                "starred_repositories"
            )
                .build()
                .also { INSTANCE = it }
        }
    }

    @Provides
    @Singleton
    fun provideStarredRepositoryDao(dataBase: com.example.data.database.StarredRepositoryDatabase) =
        dataBase.repositoryDao()
}