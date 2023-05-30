package com.example.domain.model

import com.example.data.api.ApiResult

sealed class GitResult<T> {
    data class Success<T>(val data: T) : GitResult<T>()
    data class Error<T>(val exception: Exception) : GitResult<T>()
}

fun <T> ApiResult<T>.toModelResult(): GitResult<T> {
    return when (this) {
        is ApiResult.Success -> GitResult.Success(this.data)
        is ApiResult.Error -> GitResult.Error(this.exception)
    }
}

fun <T, R> GitResult<T>.mapData(transform: (T) -> R): GitResult<R> {
    return when (this) {
        is GitResult.Success -> GitResult.Success(transform(data))
        is GitResult.Error -> GitResult.Error(this.exception)
    }
}