package com.hordiiko.osmand.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val defaultSize: Size = Size()

val MaterialTheme.size: Size
    @Composable
    get() = defaultSize

data class Size(
    val extraSmall: Dp = 16.dp,
    val small: Dp = 24.dp,
    val medium: Dp = 32.dp,
    val large: Dp = 48.dp,
    val extraLarge: Dp = 64.dp
)