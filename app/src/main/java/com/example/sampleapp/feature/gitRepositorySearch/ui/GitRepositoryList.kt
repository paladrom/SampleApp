package com.example.sampleapp.feature.gitRepositorySearch.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.R

@Composable
fun GitRepositoryList(
    gitSearchResultItems: List<com.example.domain.model.GitRepository>,
    onRepositoryClick: (com.example.domain.model.GitRepository) -> Unit,
    onStarClick: (com.example.domain.model.GitRepository) -> Unit
) {
    val starStateMap = remember { mutableStateMapOf<String, Boolean>() }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        item {
            GitSearchResultsListHeaders()
        }
        items(gitSearchResultItems) {
            val isStarred =
                it.url?.run { starStateMap.getOrPut(it.url.toString()) { it.isStarred } }
            GitRepositoryListItem(
                startText = it.name.orEmpty(),
                endText = it.ownerName.orEmpty(),
                listOnClick = { onRepositoryClick.invoke(it) },
                starOnClick = {
                    onStarClick.invoke(it)
                    val currentState = starStateMap[it.url]
                    if (it.url != null && currentState != null) {
                        starStateMap[it.url.toString()] = !currentState
                    }
                },
                isStarred = isStarred ?: false
            )
            Divider(
                color = Color.LightGray.copy(alpha = 0.5f),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun GitSearchResultsListHeaders(
) {
    Row(Modifier.padding(start = 20.dp, end = 68.dp)) {
        Text(
            text = stringResource(id = R.string.title_repository),
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = stringResource(id = R.string.title_owner),
            fontSize = 14.sp,
            modifier = Modifier.weight(0.7f)
        )
    }
}