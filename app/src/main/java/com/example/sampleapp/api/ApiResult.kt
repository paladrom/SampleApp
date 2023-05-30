package com.example.sampleapp.api

/**
 *  ネットワーク リクエストの結果モデル
 */
sealed class ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val exception: Exception) : ApiResult<T>()
}