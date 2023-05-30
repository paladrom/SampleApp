package com.example.sampleapp.feature.gitRepositorySearch.model


sealed class StarredGitRepositoriesState {
    object Init : StarredGitRepositoriesState()
    data class Success(val repositories: List<GitRepository>) : StarredGitRepositoriesState()
    data class Error(val exception: Throwable) : StarredGitRepositoriesState()

}