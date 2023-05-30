package com.example.sampleapp.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class GitRepositoryResult(
    val items: List<GitRepository>?
)