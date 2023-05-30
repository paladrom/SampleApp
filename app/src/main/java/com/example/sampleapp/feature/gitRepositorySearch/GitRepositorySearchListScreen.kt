package com.example.sampleapp.feature.gitRepositorySearch

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sampleapp.R
import com.example.sampleapp.feature.base.GitBottomNavigationBar
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepository
import com.example.sampleapp.feature.gitRepositorySearch.ui.GitRepositoryList
import com.example.sampleapp.feature.gitRepositorySearch.viewmodel.GitRepositorySearchViewModel
import com.example.sampleapp.ui.ComposeTheme
import com.example.sampleapp.ui.ErrorField
import com.example.sampleapp.ui.SearchField
import com.example.sampleapp.feature.gitRepositorySearch.model.GitSearchState
import com.example.sampleapp.ui.GitSearchAppBar


@Composable
fun GitRepositorySearchListScreen(
    navController: NavController,
    viewModel: GitRepositorySearchViewModel = hiltViewModel(),
    onRepositoryClick: (GitRepository) -> Unit
) {
    val state = viewModel.state.collectAsState(GitSearchState.Init)
    val searchText = remember { mutableStateOf("") }

    Scaffold(
        topBar = { GitSearchAppBar() },
        bottomBar = {
            GitBottomNavigationBar(
                navController = navController
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, bottom = 20.dp)
    ) { paddingValues ->
        GitRepositorySearchListScreen(
            gitSearchResultState = state.value,
            onRepositoryClick = onRepositoryClick,
            onSearchClick = { inputText ->
                searchText.value = inputText
                viewModel.performSearch(inputText)
            },
            onTryAgainClick = {
                viewModel.performSearch(searchText.value)
            },
            onStarClick = { repository -> viewModel.starOrUnstarRepository(repository) },
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        )
    }
}

@Composable
private fun GitRepositorySearchListScreen(
    gitSearchResultState: GitSearchState,
    onRepositoryClick: (GitRepository) -> Unit,
    onSearchClick: (String) -> Unit,
    onTryAgainClick: () -> Unit,
    onStarClick: (GitRepository) -> Unit,
    modifier: Modifier
) {
    remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 12.dp)
    ) {
        SearchField(
            stringResource(id = R.string.searchInputText_hint),
            onSearchClick
        )
        Spacer(Modifier.height(20.dp))
        AnimatedVisibility(
            visible = gitSearchResultState is GitSearchState.Success,
            enter = fadeIn(animationSpec = TweenSpec(500)),
            exit = fadeOut(animationSpec = TweenSpec(500)), content = {
                if (gitSearchResultState is GitSearchState.Success)
                    GitRepositoryList(
                        gitSearchResultItems = gitSearchResultState.repositories,
                        onRepositoryClick = onRepositoryClick,
                        onStarClick = onStarClick
                    )
            })

        AnimatedVisibility(
            visible = gitSearchResultState is GitSearchState.Error,
            enter = fadeIn(animationSpec = TweenSpec(500)),
            exit = fadeOut(animationSpec = TweenSpec(500))
        ) {
            ErrorField(enableTryAgain = true, onTryAgainAction = onTryAgainClick)
        }

        AnimatedVisibility(
            visible = gitSearchResultState is GitSearchState.Loading,
            enter = fadeIn(animationSpec = TweenSpec(500)),
            exit = fadeOut(animationSpec = TweenSpec(500))
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(20.dp))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StarredGitRepositoriesListScreenPreview() {
    ComposeTheme {
        GitRepositorySearchListScreen(
            gitSearchResultState = GitSearchState.Success(mockSearchRepositories()),
            onRepositoryClick = {},
            onSearchClick = {},
            onTryAgainClick = {},
            onStarClick = {},
            Modifier
        )
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun GitRepositorySearchListLoadingScreenPreview() {
    ComposeTheme {
        GitRepositorySearchListScreen(
            gitSearchResultState = GitSearchState.Loading,
            onRepositoryClick = {},
            onSearchClick = {},
            onTryAgainClick = {},
            onStarClick = {},
            Modifier
        )
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GitRepositorySearchListErrorScreenPreview() {
    ComposeTheme {
        GitRepositorySearchListScreen(
            gitSearchResultState = GitSearchState.Error(IllegalStateException()),
            onRepositoryClick = {},
            onSearchClick = {},
            onTryAgainClick = {},
            onStarClick = {},
            Modifier
        )
    }

}

private fun mockSearchRepositories(): List<GitRepository> {
    val mockRepository =
        GitRepository(
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