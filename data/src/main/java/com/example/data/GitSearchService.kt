package com.example.data

import com.example.data.dto.GitRepositoryResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject


class GitSearchService @Inject constructor(private val client: HttpClient) {

    /**
     * Git repositoryの検索を実行
     * @param searchQuery - 検索クエリ
     * @return 検索結果
     */
    suspend fun performSearch(searchQuery: String): GitRepositoryResult =
        client.get(BASE_URL) {
            parameter(PARAMETER_SEARCH_QUERY, searchQuery)
        }.body()

    companion object {
        const val BASE_URL = "https://api.github.com/search/repositories"
        const val PARAMETER_SEARCH_QUERY = "q"
    }
}