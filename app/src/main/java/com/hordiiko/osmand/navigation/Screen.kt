package com.hordiiko.osmand.navigation

const val ARG_REGION_ID = "regionId"

sealed class Screen(val route: String) {

    data object Countries : Screen("countries")

    data object Regions : Screen("regions/{$ARG_REGION_ID}") {
        fun createRoute(regionId: String): String = "regions/$regionId"
    }
}