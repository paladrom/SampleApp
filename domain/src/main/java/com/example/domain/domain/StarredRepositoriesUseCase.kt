package com.example.domain.domain

import com.example.data.StarredGitRepositoriesRepository
import com.example.domain.model.GitRepository
import com.example.domain.model.toGitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StarredRepositoriesUseCase @Inject constructor(
    private val starredGitRepositoriesRepository: StarredGitRepositoriesRepository
) {

    /**
     * セーブしたGit repositoryのDBを取得
     * @return データベースを取得の結果
     */
    fun getAll(): Flow<List<GitRepository>> {
        return starredGitRepositoriesRepository.getStarredRepositories().map {
            it.map {
                it.toGitRepository()
            }
        }
    }

    /**
     * セーブしたGit repositoryのDBを取得
     * @return データベースを取得の結果
     */
    suspend fun getSingle(gitRepositoryUrl: String): GitRepository? {
        return starredGitRepositoriesRepository.getStarredRepository(gitRepositoryUrl)
            ?.toGitRepository()
    }
}