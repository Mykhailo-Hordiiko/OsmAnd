package com.hordiiko.osmand.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = appColorScheme,
        content = content
    )
}

private val appColorScheme: ColorScheme =
    lightColorScheme(
        primary = topBar,
        background = activityBackground,
        onBackground = text,
        surface = itemBackground,
        onSurface = text,
        outline = divider
    )