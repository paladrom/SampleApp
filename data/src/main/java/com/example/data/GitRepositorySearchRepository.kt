package com.example.data

import com.example.data.api.ApiResult
import com.example.data.dto.GitRepositoryResult
import com.example.sampleapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.UnknownServiceException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GitRepositorySearchRepository @Inject constructor(
    private val gitSearchService: GitSearchService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    var lastSuccessfulSearchResult: GitRepositoryResult? = null
    /**
     * Git repositoryの検索を実行
     * @param searchQuery - 検索クエリ
     * @return 検索結果
     */
    suspend fun searchGitRepositories(searchQuery: String): ApiResult<GitRepositoryResult> =
        withContext(ioDispatcher) {
            try {
                val result = gitSearchService.performSearch(searchQuery)
                if (result.items != null) {
                    cacheGitRepositorySearchResult(result)
                    ApiResult.Success(result)
                } else {
                    ApiResult.Error(UnknownServiceException())
                }
            } catch (e: Exception) {
                Timber.e(e, e.message)
                ApiResult.Error(e)
            }
        }

    private fun cacheGitRepositorySearchResult(newResult: GitRepositoryResult) {
        lastSuccessfulSearchResult = newResult
    }

    fun getGitRepositoryResultCache(): GitRepositoryResult? {
        return lastSuccessfulSearchResult
    }
}