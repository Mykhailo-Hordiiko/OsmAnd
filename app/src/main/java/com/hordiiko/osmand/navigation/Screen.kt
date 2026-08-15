package com.hordiiko.osmand.navigation

sealed class Screen(val route: String) {

    data object Countries : Screen("countries")
    data object Regions : Screen("regions")
}