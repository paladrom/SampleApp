package com.example.sampleapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    startText: String? = null,
    endText: String? = null,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClick.invoke() }
            .padding(vertical = 18.dp, horizontal = 20.dp)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        startText?.run {
            Text(
                text = startText,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
        }
        endText?.run {
            Text(
                text = endText,
                fontSize = 16.sp,
                modifier = Modifier.weight(0.7f)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ListItemPreview() {
    ComposeTheme {
        Surface {
            ListItem(startText = "ItemTitle", endText = "ItemDescription", onClick = {})
        }
    }
}
