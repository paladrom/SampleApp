package com.example.sampleapp.feature.gitRepositorySearch.domain

import com.example.data.GitRepositorySearchRepository
import com.example.data.dto.GitRepositoryResult
import javax.inject.Inject


class GetGitRepositoriesResultCacheUseCase @Inject constructor(
    private val gitRepositorySearchRepository: GitRepositorySearchRepository
) {
    /**
     * Git repositoryの検索結果のキャッシュを取得
     * @return キャッシュを取得の結果
     */
    operator fun invoke(): GitRepositoryResult? {
        return gitRepositorySearchRepository.getGitRepositoryResultCache()
    }
}