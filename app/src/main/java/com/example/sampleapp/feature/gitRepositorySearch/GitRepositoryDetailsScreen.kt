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
import com.example.sampleapp.ui.ErrorField
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepository
import com.example.sampleapp.feature.gitRepositorySearch.model.GitRepositoryDetailsState
import com.example.sampleapp.feature.gitRepositorySearch.viewmodel.GitRepositoryDetailsViewModel


@Composable
fun GitRepositoryDetailsScreen(
    viewModel: GitRepositoryDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState(GitRepositoryDetailsState.Init)
    GitRepositoryDetailsScreen(
        state.value
    )
}

@Composable
private fun GitRepositoryDetailsScreen(
    state: GitRepositoryDetailsState
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 20.dp)
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
                is GitRepositoryDetailsState.Success -> {
                    GitRepositoryImageView(repository = state.repository)
                    GitRepositoryDetailsView(repository = state.repository)
                }

                is GitRepositoryDetailsState.Error -> {
                    ErrorField {}
                }

                GitRepositoryDetailsState.Init -> {}
            }
        }
    }
}

@Composable
fun GitRepositoryImageView(
    repository: GitRepository
) {
    AsyncImage(
        model = repository.ownerIconUrl,
        placeholder = painterResource(id = R.drawable.placeholder),
        contentDescription = stringResource(id = R.string.repository_image_description)
    )
}

@Composable
fun GitRepositoryDetailsView(
    repository: GitRepository
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
        state = GitRepositoryDetailsState.Success(mockRepository())
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun GitRepositoryDetailsErrorScreenPreview() {
    GitRepositoryDetailsScreen(
        state = GitRepositoryDetailsState.Error(IllegalStateException())
    )
}

private fun mockRepository(): GitRepository =
    GitRepository(
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