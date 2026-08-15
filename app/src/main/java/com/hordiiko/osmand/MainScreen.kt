package com.hordiiko.osmand

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hordiiko.osmand.navigation.MainNavHost

@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()

    MainNavHost(
        navController = navController
    )
}