package com.example.sampleapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.R

@Composable
fun SearchField(
    hintText: String = stringResource(id = R.string.action_search),
    onSearchAction: (String) -> Unit
) {
    val inputText = remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .padding(horizontal = 20.dp)
    ) {
        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            label = { Text(hintText) },
            leadingIcon = {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = stringResource(id = R.string.action_search),
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchAction.invoke(inputText.value) }),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchFieldPreview() {
    ComposeTheme {
        Surface {
            SearchField(hintText = "SearchHint") {}
        }
    }
}