package com.example.domain.domain

import com.example.data.GitRepositorySearchRepository
import com.example.domain.model.GitRepository
import com.example.domain.model.GitResult
import com.example.domain.model.mapData
import com.example.domain.model.toGitRepositoryModel
import com.example.domain.model.toModelResult
import javax.inject.Inject


class SearchGitRepositoriesUseCase @Inject constructor(
    private val gitRepositorySearchRepository: GitRepositorySearchRepository
) {

    /**
     * Git repositoryの検索を実行
     * @param searchText - 検索クエリ
     * @return 検索結果
     */
    suspend operator fun invoke(searchText: String): GitResult<List<GitRepository>?> {
        return gitRepositorySearchRepository.searchGitRepositories(searchText).toModelResult()
            .mapData { it.items?.map { it.toGitRepositoryModel(false) } }
    }
}