package com.example.domain.model


sealed class GitSearchState {
    object Init : GitSearchState()
    object Loading : GitSearchState()
    data class Success(val repositories: List<GitRepository> = emptyList()) : GitSearchState()
    data class Error(val exception: Throwable) : GitSearchState()
}