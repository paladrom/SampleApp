package com.example.sampleapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.R

@Composable
fun ErrorField(
    errorText: String = stringResource(id = R.string.error_generic),
    enableTryAgain: Boolean = false,
    onTryAgainAction: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Text(text = errorText)
        if (enableTryAgain) {
            Button(
                onClick = { onTryAgainAction.invoke() },
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Text(text = stringResource(id = R.string.action_try_again))
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ErrorFieldPreview() {
    ComposeTheme {
        Surface {
            ErrorField(
                errorText = stringResource(id = R.string.error_generic),
                enableTryAgain = true
            ) {}
        }
    }
}