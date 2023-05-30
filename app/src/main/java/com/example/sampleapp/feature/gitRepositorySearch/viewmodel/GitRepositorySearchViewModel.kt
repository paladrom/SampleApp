
package com.example.sampleapp.feature.gitRepositorySearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.ApiResult
import com.example.sampleapp.feature.gitRepositorySearch.domain.SearchGitRepositoriesUseCase
import com.example.sampleapp.feature.gitRepositorySearch.domain.StarOrUnstarRepositoryUseCase
import com.example.sampleapp.feature.gitRepositorySearch.domain.StarredRepositoriesUseCase
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepository
import com.example.sampleapp.feature.gitRepositorySearch.model.GitSearchState
import com.example.sampleapp.feature.gitRepositorySearch.model.toGitRepositoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GitRepositorySearchViewModel @Inject constructor(
    private val searchGitRepositoriesUseCase: SearchGitRepositoriesUseCase,
    private val starRepositoriesUseCase: StarOrUnstarRepositoryUseCase,
    private val starredRepositoriesUseCase: StarredRepositoriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<GitSearchState>(GitSearchState.Init)
    val state = _state.asStateFlow()

    init {
        observeDatabaseUpdates()
    }

    /**
     * 検索を実行
     * @param inputText - 検索クエリ
     * @return 検索結果
     */
    fun performSearch(inputText: String) {
        _state.value = GitSearchState.Loading
        viewModelScope.launch {
            try {
                val result = searchGitRepositoriesUseCase.invoke(inputText)
                when (result) {
                    is ApiResult.Success -> {
                        _state.update {
                            GitSearchState.Success(
                                result.data.items?.map {
                                    it.toGitRepositoryModel(isRepositoryStarred(it.url))
                                }.orEmpty()
                            )
                        }
                    }

                    is ApiResult.Error -> {
                        _state.value = GitSearchState.Error(result.exception)
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                _state.value = GitSearchState.Error(e)
                Timber.e(e, e.message)
            }
        }
    }

    private fun observeDatabaseUpdates() {
        viewModelScope.launch {
            starredRepositoriesUseCase.getAll().collect { databaseRepositories ->
                val searchState = state.value
                if (searchState is GitSearchState.Success) {
                    _state.update {
                        GitSearchState.Success(
                            searchState.repositories.map { repository ->
                                repository.copy(isStarred = isRepositoryStarred(repository.url))
                            }
                        )
                    }
                }
            }
        }
    }

    private suspend fun isRepositoryStarred(repositoryUrl: String?): Boolean {
        repositoryUrl ?: return false
        return starredRepositoriesUseCase.getSingle(repositoryUrl) != null
    }

    fun starOrUnstarRepository(repository: GitRepository) {
        viewModelScope.launch {
            val success = starRepositoriesUseCase.invoke(repository)
            if (!success) {
                Timber.e("Database Error: Unable to write or delete data")
            }
        }
    }
}