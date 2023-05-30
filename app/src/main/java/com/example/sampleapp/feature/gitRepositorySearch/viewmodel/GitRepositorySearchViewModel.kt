
package com.example.sampleapp.feature.gitRepositorySearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.GitResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GitRepositorySearchViewModel @Inject constructor(
    private val searchGitRepositoriesUseCase: com.example.domain.domain.SearchGitRepositoriesUseCase,
    private val starRepositoriesUseCase: com.example.domain.domain.StarOrUnstarRepositoryUseCase,
    private val starredRepositoriesUseCase: com.example.domain.domain.StarredRepositoriesUseCase
) : ViewModel() {
    private val _state =
        MutableStateFlow<com.example.domain.model.GitSearchState>(com.example.domain.model.GitSearchState.Init)
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
        _state.value = com.example.domain.model.GitSearchState.Loading
        viewModelScope.launch {
            try {
                val result = searchGitRepositoriesUseCase.invoke(inputText)
                when (result) {
                    is GitResult.Success -> {
                        _state.update {
                            com.example.domain.model.GitSearchState.Success(
                                result.data?.map { it.copy(isStarred = isRepositoryStarred(it.url.toString())) }
                                    .orEmpty()
                            )
                        }
                    }

                    is GitResult.Error -> {
                        _state.value =
                            com.example.domain.model.GitSearchState.Error(result.exception)
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                _state.value = com.example.domain.model.GitSearchState.Error(e)
                Timber.e(e, e.message)
            }
        }
    }

    private fun observeDatabaseUpdates() {
        viewModelScope.launch {
            starredRepositoriesUseCase.getAll().collect { databaseRepositories ->
                val searchState = state.value
                if (searchState is com.example.domain.model.GitSearchState.Success) {
                    _state.update {
                        com.example.domain.model.GitSearchState.Success(
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

    fun starOrUnstarRepository(repository: com.example.domain.model.GitRepository) {
        viewModelScope.launch {
            val success = starRepositoriesUseCase.invoke(repository)
            if (!success) {
                Timber.e("Database Error: Unable to write or delete data")
            }
        }
    }
}