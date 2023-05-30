package com.example.sampleapp.feature.gitRepositorySearch

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sampleapp.feature.base.GitBottomNavigationBar
import com.example.sampleapp.feature.gitRepositorySearch.ui.GitRepositoryList
import com.example.sampleapp.feature.gitRepositorySearch.viewmodel.StarredGitRepositoriesViewModel
import com.example.sampleapp.ui.ComposeTheme
import com.example.sampleapp.ui.GitSearchAppBar

@Composable
fun StarredGitRepositoriesScreen(
    viewModel: StarredGitRepositoriesViewModel = hiltViewModel(),
    navController: NavController,
    onRepositoryClick: (com.example.domain.model.GitRepository) -> Unit,
) {
    val state = viewModel.state.collectAsState(emptyList())

    Scaffold(
        topBar = {GitSearchAppBar()},
        bottomBar = {
            GitBottomNavigationBar(
                navController = navController
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, bottom = 20.dp)
    ) {paddingParameters ->
        StarredGitRepositoriesScreen(
            state = state.value,
            onStarClick = { repository -> viewModel.starOrUnstarRepository(repository) },
            onRepositoryClick = onRepositoryClick,
            paddingParameters
        )
    }
}

@Composable
fun StarredGitRepositoriesScreen(
    state: List<com.example.domain.model.GitRepository>,
    onStarClick: (com.example.domain.model.GitRepository) -> Unit,
    onRepositoryClick: (com.example.domain.model.GitRepository) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 20.dp, bottom = paddingValues.calculateBottomPadding())
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(animationSpec = TweenSpec(500)),
            exit = fadeOut(animationSpec = TweenSpec(500))
        ) {
            GitRepositoryList(
                gitSearchResultItems = state,
                onRepositoryClick = onRepositoryClick,
                onStarClick = onStarClick
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StarredGitRepositoriesListScreenPreview() {
    ComposeTheme {
        StarredGitRepositoriesScreen(
            state = mockSavedRepositories(),
            onRepositoryClick = {},
            onStarClick = {},
            paddingValues = PaddingValues()
        )
    }
}

private fun mockSavedRepositories(): List<com.example.domain.model.GitRepository> {
    val mockRepository =
        com.example.domain.model.GitRepository(
            name = "nowInAndroid",
            ownerName = "android",
            ownerIconUrl = null,
            url = "",
            language = null,
            stargazersCount = null,
            watchersCount = null,
            forksCount = null,
            openIssuesCount = null,
            isStarred = false
        )
    return listOf(
        mockRepository,
        mockRepository.copy(name = "iosched", isStarred = true),
        mockRepository.copy("tivi")
    )
}