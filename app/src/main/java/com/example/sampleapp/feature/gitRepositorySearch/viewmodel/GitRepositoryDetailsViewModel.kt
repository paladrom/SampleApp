package com.example.sampleapp.feature.gitRepositorySearch.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.GitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GitRepositoryDetailsViewModel @Inject constructor(
    private val getGitRepositoriesResultCacheUseCase: com.example.domain.domain.GetGitRepositoriesResultCacheUseCase,
    private val starredRepositoriesUseCase: com.example.domain.domain.StarredRepositoriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state =
        MutableStateFlow<com.example.domain.model.GitRepositoryDetailsState>(com.example.domain.model.GitRepositoryDetailsState.Init)
    val state = _state.asStateFlow()
    private val repositoryUrl = savedStateHandle.get<String>(REPOSITORY_URL_STATE_KEY)!!
    private val isStarred = savedStateHandle.get<Boolean>(REPOSITORY_STARRED_STATE_KEY)!!

    init {
        viewModelScope.launch {
            getGitRepository()
        }
    }

    private suspend fun getGitRepository() {
        try {
            val repository = if (isStarred) {
                starredRepositoriesUseCase.getSingle(repositoryUrl)
            } else {
                getCachedRepository(repositoryUrl)
            }
            repository ?: throw (IllegalArgumentException("Repository missing"))
            _state.value = com.example.domain.model.GitRepositoryDetailsState.Success(repository)
        } catch (e: Exception) {
            _state.value = com.example.domain.model.GitRepositoryDetailsState.Error(e)
            Timber.e(e, e.message)
        }
    }

    private suspend fun getCachedRepository(repositoryUrl: String): GitRepository? {
        val repository = getCachedRepositoriesResult()?.find { it.url == repositoryUrl }
        repository ?: return null
        return repository.copy(isStarred = starredRepositoriesUseCase.getSingle(repositoryUrl) != null)
    }

    private fun getCachedRepositoriesResult(): List<GitRepository>? {
        return getGitRepositoriesResultCacheUseCase.invoke()
    }

    companion object {
        private const val REPOSITORY_URL_STATE_KEY = "repositoryUrl"
        private const val REPOSITORY_STARRED_STATE_KEY = "isStarred"
    }
}