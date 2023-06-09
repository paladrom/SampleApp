package com.example.data

import com.example.data.database.dao.StarredRepositoryDao
import com.example.data.database.entity.StarredRepositoryEntity
import com.example.sampleapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StarredGitRepositoriesRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val databaseAo: StarredRepositoryDao
) {

    suspend fun insertStarredRepository(entity: StarredRepositoryEntity) =
        withContext(ioDispatcher) {
            databaseAo.insert(entity)
        }

    suspend fun removeStarredRepository(entity: StarredRepositoryEntity) =
        withContext(ioDispatcher) {
            databaseAo
                .deleteRepository(entity)
        }

    fun getStarredRepositories(): Flow<List<StarredRepositoryEntity>> {
        return databaseAo.getAll()
    }

    suspend fun getStarredRepository(gitRepositoryUrl: String): StarredRepositoryEntity? {
        return databaseAo
            .getRepository(gitRepositoryUrl)
    }


}