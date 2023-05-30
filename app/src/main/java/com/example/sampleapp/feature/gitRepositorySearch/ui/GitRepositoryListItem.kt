package com.example.sampleapp.feature.gitRepositorySearch.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.R
import com.example.sampleapp.ui.ComposeTheme
import com.example.sampleapp.ui.ListItem

@Composable
fun GitRepositoryListItem(
    isStarred: Boolean,
    modifier: Modifier = Modifier,
    startText: String? = null,
    endText: String? = null,
    listOnClick: () -> Unit,
    starOnClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            ListItem(
                modifier = modifier.padding(end = 48.dp),
                startText = startText,
                endText = endText,
                onClick = listOnClick
            )
            IconButton(
                onClick = { starOnClick.invoke() }
            ) {
                Icon(
                    imageVector = if (isStarred) {
                        Icons.Filled.Star
                    } else {
                        Icons.Outlined.StarRate
                    },
                    contentDescription = stringResource(id = R.string.action_search)
                )
            }
        }

    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ListItemPreview() {
    ComposeTheme {
        Surface {
            GitRepositoryListItem(
                startText = "ItemTitle",
                endText = "ItemDescription",
                isStarred = true,
                starOnClick = {},
                listOnClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ListItemUnstarredPreview() {
    ComposeTheme {
        Surface {
            GitRepositoryListItem(
                startText = "ItemTitle",
                endText = "ItemDescription",
                isStarred = false,
                starOnClick = {},
                listOnClick = {}
            )
        }
    }
}