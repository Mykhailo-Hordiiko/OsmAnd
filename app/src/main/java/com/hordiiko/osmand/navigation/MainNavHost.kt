package com.hordiiko.osmand.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hordiiko.osmand.presentation.CountriesScreen
import com.hordiiko.osmand.presentation.RegionsScreen

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        startDestination = Screen.Countries.route,
        navController = navController
    ) {
        composable(Screen.Countries.route) {
            CountriesScreen(
                onCountrySelected = {
                    navController.navigateTo(Screen.Regions)
                }
            )
        }
        composable(Screen.Regions.route) {
            RegionsScreen(
                onBackClick = {
                    navController.goBack()
                }
            )
        }
    }
}