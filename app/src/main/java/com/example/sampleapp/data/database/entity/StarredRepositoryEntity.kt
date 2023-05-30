package com.example.sampleapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "starred_repositories")
data class StarredRepositoryEntity(
    @PrimaryKey
    val url: String,
    val name: String,
    val owner: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long?,
    val watchersCount: Long?,
    val forksCount: Long?,
    val openIssuesCount: Long?
)