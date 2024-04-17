package com.ndc.cinfoadmin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ndc.cinfoadmin.ui.screen.home.HomeScreen

@Composable
fun SetupNavHost(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = NavRoute.HOME.route,
        route = NavRoute.Root.route
    ) {
        composable(
            route = NavRoute.HOME.route
        ) {
            HomeScreen(navHostController = navHostController)
        }
    }
}