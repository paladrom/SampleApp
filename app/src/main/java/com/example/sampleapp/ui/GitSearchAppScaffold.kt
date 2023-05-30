package com.example.sampleapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sampleapp.R

@Composable
fun GitSearchAppBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight().padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Text(
            stringResource(id = R.string.app_name),
            fontSize = 24.sp
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppBar() {
    ComposeTheme {
        GitSearchAppBar()
    }
}