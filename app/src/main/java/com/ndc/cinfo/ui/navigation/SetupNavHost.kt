package com.ndc.cinfo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ndc.cinfo.ui.screen.login.LoginScreen
import com.ndc.cinfo.ui.screen.register.RegisterScreen

@Composable
fun SetupNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoute.LOGIN.route,
        route = NavRoute.ROOT.route
    ) {
        composable(
            route = NavRoute.LOGIN.route
        ) {
            LoginScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoute.REGISTER.route
        ) {
            RegisterScreen(navHostController = navHostController)
        }
    }
}