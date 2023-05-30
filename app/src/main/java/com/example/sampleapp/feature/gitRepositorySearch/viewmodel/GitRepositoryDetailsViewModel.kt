package com.example.sampleapp.feature.gitRepositorySearch.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.feature.gitRepositorySearch.domain.GetGitRepositoriesResultCacheUseCase
import com.example.sampleapp.feature.gitRepositorySearch.domain.StarredRepositoriesUseCase
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepository
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepositoryDetailsState
import com.example.sampleapp.feature.gitRepositorySearch.model.toGitRepositoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GitRepositoryDetailsViewModel @Inject constructor(
    private val getGitRepositoriesResultCacheUseCase: GetGitRepositoriesResultCacheUseCase,
    private val starredRepositoriesUseCase: StarredRepositoriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<GitRepositoryDetailsState>(GitRepositoryDetailsState.Init)
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
            _state.value = GitRepositoryDetailsState.Success(repository)
        } catch (e: Exception) {
            _state.value = GitRepositoryDetailsState.Error(e)
            Timber.e(e, e.message)
        }
    }

    private suspend fun getCachedRepository(repositoryUrl: String): GitRepository? {
        val repository = getCachedRepositoriesResult()?.items?.find { it.url == repositoryUrl }
        repository ?: return null
        return repository.toGitRepositoryModel(starredRepositoriesUseCase.getSingle(repositoryUrl) != null)
    }

    private fun getCachedRepositoriesResult(): com.example.data.dto.GitRepositoryResult? {
        return getGitRepositoriesResultCacheUseCase.invoke()
    }

    companion object {
        private const val REPOSITORY_URL_STATE_KEY = "repositoryUrl"
        private const val REPOSITORY_STARRED_STATE_KEY = "isStarred"
    }
}