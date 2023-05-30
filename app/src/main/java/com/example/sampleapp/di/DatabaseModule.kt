package com.example.sampleapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.sampleapp.data.database.StarredRepositoryDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Volatile
    private var INSTANCE: StarredRepositoryDatabase? = null

    @Provides
    @Singleton
    fun providesStarredRepositoryDatabase(@ApplicationContext context: Context): StarredRepositoryDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context,
                StarredRepositoryDatabase::class.java,
                "starred_repositories"
            )
                .build()
                .also { INSTANCE = it }
        }
    }

    @Provides
    @Singleton
    fun provideStarredRepositoryDao(dataBase: StarredRepositoryDatabase) = dataBase.repositoryDao()
}