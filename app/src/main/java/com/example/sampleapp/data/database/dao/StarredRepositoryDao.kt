package com.example.sampleapp.data.database.dao

import androidx.room.*
import com.example.sampleapp.data.database.entity.StarredRepositoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StarredRepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: StarredRepositoryEntity)

    @Query("SELECT * FROM starred_repositories WHERE url = :url")
    suspend fun getRepository(url: String): StarredRepositoryEntity?

    @Delete
    suspend fun deleteRepository(entity: StarredRepositoryEntity): Int

    //returns number of sucessfully deleted rows
    @Query("SELECT * FROM starred_repositories")
    fun getAll(): Flow<List<StarredRepositoryEntity>>
}