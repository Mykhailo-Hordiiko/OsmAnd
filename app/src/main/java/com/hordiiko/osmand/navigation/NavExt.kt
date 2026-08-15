package com.hordiiko.osmand.navigation

import androidx.navigation.NavController

fun NavController.goBack(): Boolean =
    popBackStack()

fun NavController.navigateTo(screen: Screen) {
    navigate(screen.route) {
        launchSingleTop = true
    }
}