package com.example.sampleapp.feature.gitRepositorySearch.domain

import com.example.data.GitRepositorySearchRepository
import com.example.data.api.ApiResult
import com.example.data.dto.GitRepositoryResult
import javax.inject.Inject


class SearchGitRepositoriesUseCase @Inject constructor(
    private val gitRepositorySearchRepository: GitRepositorySearchRepository
) {

    /**
     * Git repositoryの検索を実行
     * @param searchText - 検索クエリ
     * @return 検索結果
     */
    suspend operator fun invoke(searchText: String): ApiResult<GitRepositoryResult> {
        return gitRepositorySearchRepository.searchGitRepositories(searchText)
    }
}