package com.example.sampleapp.feature.gitRepositorySearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class StarredGitRepositoriesViewModel @Inject constructor(
    starredRepositoriesUseCase: com.example.domain.domain.StarredRepositoriesUseCase,
    private val starOrUnstarRepositoryUseCase: com.example.domain.domain.StarOrUnstarRepositoryUseCase
) : ViewModel() {
    val state = starredRepositoriesUseCase.getAll()

    fun starOrUnstarRepository(repository: com.example.domain.model.GitRepository) {
        viewModelScope.launch {
            val success = starOrUnstarRepositoryUseCase.invoke(repository)
            if (!success) {
                Timber.e("Database Error: Unable to write or delete data")
            }
        }
    }

}