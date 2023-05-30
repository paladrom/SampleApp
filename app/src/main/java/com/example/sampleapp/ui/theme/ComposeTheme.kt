package com.example.sampleapp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.sampleapp.ui.theme.*

@Composable
fun ComposeTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) {
        darkColors(
            primary = darkBlue,
            primaryVariant = darkBlueVariant,
            secondary = darkBlueSecondary,
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColors(
            primary = lightBlue,
            primaryVariant = lightBlueVariant,
            secondary = lightBlueSecondary,
            background = Color.White,
            surface = Color.White,
            onPrimary = Color.Black,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black
        )
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}