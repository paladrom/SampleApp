package com.example.sampleapp.feature.gitRepositorySearch.domain

import com.example.sampleapp.data.StarredGitRepositoriesRepository
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepository
import com.example.sampleapp.feature.gitRepositorySearch.model.toGitRepository
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