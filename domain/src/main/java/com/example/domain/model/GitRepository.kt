package com.example.domain.model

import com.example.data.dto.GitRepository as DTOGitRepository

data class GitRepository(
    val name: String?,
    val ownerName: String?,
    val ownerIconUrl: String?,
    val url: String?,
    val language: String?,
    val stargazersCount: Long?,
    val watchersCount: Long?,
    val forksCount: Long?,
    val openIssuesCount: Long?,
    val isStarred: Boolean
)

fun DTOGitRepository.toGitRepositoryModel(isStarred: Boolean): GitRepository {
    return GitRepository(
        name = this.name,
        ownerName = this.owner?.name,
        url = this.url,
        language = this.language,
        stargazersCount = this.stars,
        watchersCount = this.watchers,
        forksCount = this.forks,
        openIssuesCount = this.openIssues,
        isStarred = isStarred,
        ownerIconUrl = this.owner?.avatarUrl
    )
}

fun GitRepository.toStarredRepositoryEntity(): com.example.data.database.entity.StarredRepositoryEntity {
    return com.example.data.database.entity.StarredRepositoryEntity(
        name = name.orEmpty(),
        url = url.orEmpty(),
        owner = ownerName.orEmpty(),
        ownerIconUrl = ownerIconUrl.orEmpty(),
        language = language.orEmpty(),
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        openIssuesCount = openIssuesCount
    )
}

fun com.example.data.database.entity.StarredRepositoryEntity.toGitRepository(): GitRepository {
    return GitRepository(
        name = name,
        url = url,
        ownerName = owner,
        ownerIconUrl = ownerIconUrl,
        language = language,
        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        openIssuesCount = openIssuesCount,
        isStarred = true
    )
}