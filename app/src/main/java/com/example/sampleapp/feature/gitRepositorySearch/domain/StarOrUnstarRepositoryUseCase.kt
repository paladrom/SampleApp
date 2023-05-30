package com.example.sampleapp.feature.gitRepositorySearch.domain

import com.example.data.StarredGitRepositoriesRepository
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepository
import com.example.sampleapp.feature.gitRepositorySearch.model.toStarredRepositoryEntity
import javax.inject.Inject


class StarOrUnstarRepositoryUseCase @Inject constructor(
    private val starredGitRepositoriesRepository: StarredGitRepositoriesRepository
) {
    /**
     * Git repositoryをセーブしたかどうかに応じて、データベースに挿入、削除
     * @param GitRepository
     * @return 操作が成功したのBoolean
     */
    suspend operator fun invoke(gitRepository: GitRepository): Boolean {
        return if (!gitRepository.isStarred) {
            starRepository(gitRepository)
        } else {
            unStarRepository(gitRepository)
        }
    }

    /**
     * Git repositoryをデータベースに挿入
     * @param Git repository
     * @return 操作が成功したのBoolean
     */
    private suspend fun starRepository(gitRepository: GitRepository): Boolean {
        gitRepository.url ?: return false
        val entity = gitRepository.toStarredRepositoryEntity()
        starredGitRepositoriesRepository.insertStarredRepository(entity)
        return true
    }

    /**
     * データベースからリポジトリを削除
     * @param GitRepository
     * @return 操作が成功したのBoolean
     */
    private suspend fun unStarRepository(gitRepository: GitRepository): Boolean {
        gitRepository.url ?: return false
        val numberOfDeleted = starredGitRepositoriesRepository.removeStarredRepository(
            gitRepository.toStarredRepositoryEntity()
        )
        return numberOfDeleted > 0
    }
}