package com.hordiiko.osmand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import com.hordiiko.osmand.presentation.theme.AppTheme
import com.hordiiko.osmand.presentation.theme.statusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(statusBarColor.toArgb())
        )

        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}