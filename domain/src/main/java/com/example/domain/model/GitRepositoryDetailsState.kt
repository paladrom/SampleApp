package com.example.domain.model


sealed class GitRepositoryDetailsState {
    object Init : GitRepositoryDetailsState()
    data class Success(val repository: GitRepository) : GitRepositoryDetailsState()
    data class Error(val exception: Throwable) : GitRepositoryDetailsState()
}