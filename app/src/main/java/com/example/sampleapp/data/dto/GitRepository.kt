package com.example.sampleapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GitRepository(
    val name: String?,
    val owner: Owner?,
    val url: String?,
    val language: String?,
    @SerialName("stargazers_count")
    val stars: Long?,
    @SerialName("watchers_count")
    val watchers: Long?,
    @SerialName("forks_count")
    val forks: Long?,
    @SerialName("open_issues_count")
    val openIssues: Long?
)