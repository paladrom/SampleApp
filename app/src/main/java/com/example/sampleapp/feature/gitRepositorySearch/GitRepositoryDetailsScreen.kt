package com.example.sampleapp.feature.gitRepositorySearch


import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.sampleapp.R
import com.example.sampleapp.feature.gitRepositorySearch.viewmodel.GitRepositoryDetailsViewModel
import com.example.sampleapp.ui.ErrorField
import com.example.sampleapp.ui.GitSearchAppBar


@Composable
fun GitRepositoryDetailsScreen(
    viewModel: GitRepositoryDetailsViewModel = hiltViewModel()
) {
    val state =
        viewModel.state.collectAsState(com.example.domain.model.GitRepositoryDetailsState.Init)
    GitRepositoryDetailsScreen(
        state.value
    )
}

@Composable
private fun GitRepositoryDetailsScreen(
    state: com.example.domain.model.GitRepositoryDetailsState
) {
    Scaffold(
        topBar = { GitSearchAppBar() },
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, bottom = 20.dp)
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp, bottom = paddingValues.calculateBottomPadding())
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            when (state) {
                is com.example.domain.model.GitRepositoryDetailsState.Success -> {
                    GitRepositoryImageView(repository = state.repository)
                    GitRepositoryDetailsView(repository = state.repository)
                }

                is com.example.domain.model.GitRepositoryDetailsState.Error -> {
                    ErrorField {}
                }

                com.example.domain.model.GitRepositoryDetailsState.Init -> {}
            }
        }
    }
}

@Composable
fun GitRepositoryImageView(
    repository: com.example.domain.model.GitRepository
) {
    AsyncImage(
        model = repository.ownerIconUrl,
        placeholder = painterResource(id = R.drawable.placeholder),
        contentDescription = stringResource(id = R.string.repository_image_description)
    )
}

@Composable
fun GitRepositoryDetailsView(
    repository: com.example.domain.model.GitRepository
) {
    Row(modifier = Modifier.padding(20.dp)) {
        Spacer(modifier = Modifier.weight(0.2f))
        Column(
            horizontalAlignment = Alignment.Start, modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = stringResource(
                    id = R.string.written_language,
                    repository.language.orEmpty()
                )
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = pluralStringResource(
                    id = R.plurals.stars_count,
                    repository.stargazersCount?.toInt() ?: 0
                )
            )
            Text(
                text = pluralStringResource(
                    id = R.plurals.forks_count,
                    repository.forksCount?.toInt() ?: 0
                )
            )
            Text(
                text = pluralStringResource(
                    id = R.plurals.open_issues_count,
                    repository.openIssuesCount?.toInt() ?: 0
                )
            )
        }
        Spacer(modifier = Modifier.weight(0.2f))
    }


}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun GitRepositoryDetailsScreenPreview() {
    GitRepositoryDetailsScreen(
        state = com.example.domain.model.GitRepositoryDetailsState.Success(mockRepository())
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun GitRepositoryDetailsErrorScreenPreview() {
    GitRepositoryDetailsScreen(
        state = com.example.domain.model.GitRepositoryDetailsState.Error(IllegalStateException())
    )
}

private fun mockRepository(): com.example.domain.model.GitRepository =
    com.example.domain.model.GitRepository(
        name = "nowInAndroid",
        ownerName = "android",
        ownerIconUrl = "",
        url = "",
        language = "Kotlin",
        stargazersCount = 2132,
        watchersCount = 1028,
        forksCount = 9736,
        openIssuesCount = 27,
        isStarred = false
    )