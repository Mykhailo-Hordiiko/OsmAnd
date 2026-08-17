package com.hordiiko.osmand.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hordiiko.osmand.presentation.RegionsScreen
import com.hordiiko.osmand.presentation.countries.CountriesScreen

@Composable
fun MainNavHost(navController: NavHostController) {
    val navigateToRegion: (String) -> Unit = { regionId ->
        navController.navigate(Screen.Regions.createRoute(regionId))
    }

    NavHost(
        modifier = Modifier.fillMaxSize(),
        startDestination = Screen.Countries.route,
        navController = navController
    ) {
        composable(
            route = Screen.Countries.route
        ) {
            CountriesScreen(
                onNodeClick = navigateToRegion
            )
        }

        composable(
            route = Screen.Regions.route,
            arguments = listOf(
                navArgument(ARG_REGION_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val regionId: String =
                backStackEntry.arguments
                    ?.getString(ARG_REGION_ID)
                    ?: return@composable

            RegionsScreen(
                regionId = regionId,
                onNodeClick = navigateToRegion,
                onBackClick = { navController.goBack() }
            )
        }
    }
}