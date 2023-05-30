package com.example.sampleapp.feature.gitRepositorySearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.sampleapp.feature.gitRepositorySearch.domain.StarOrUnstarRepositoryUseCase
import com.example.sampleapp.feature.gitRepositorySearch.domain.StarredRepositoriesUseCase
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class StarredGitRepositoriesViewModel @Inject constructor(
    starredRepositoriesUseCase: StarredRepositoriesUseCase,
    private val starOrUnstarRepositoryUseCase: StarOrUnstarRepositoryUseCase
) : ViewModel() {
    val state = starredRepositoriesUseCase.getAll()

    fun starOrUnstarRepository(repository: GitRepository) {
        viewModelScope.launch {
            val success = starOrUnstarRepositoryUseCase.invoke(repository)
            if (!success) {
                Timber.e("Database Error: Unable to write or delete data")
            }
        }
    }

}