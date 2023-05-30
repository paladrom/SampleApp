package com.example.domain.domain

import com.example.data.GitRepositorySearchRepository
import com.example.domain.model.GitRepository
import com.example.domain.model.toGitRepositoryModel
import javax.inject.Inject


class GetGitRepositoriesResultCacheUseCase @Inject constructor(
    private val gitRepositorySearchRepository: GitRepositorySearchRepository
) {
    /**
     * Git repositoryの検索結果のキャッシュを取得
     * @return キャッシュを取得の結果
     */
    operator fun invoke(): List<GitRepository>? {
        return gitRepositorySearchRepository.getGitRepositoryResultCache()?.items?.map {
            it.toGitRepositoryModel(
                false
            )
        }
    }
}