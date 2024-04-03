package com.ndc.cinfo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ndc.cinfo.ui.screen.login.LoginScreen
import com.ndc.cinfo.ui.screen.main.MainScreen
import com.ndc.cinfo.ui.screen.register.RegisterScreen

@Composable
fun SetupNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoute.Login.route,
        route = NavRoute.Root.route
    ) {
        composable(
            route = NavRoute.Login.route
        ) {
            LoginScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoute.Register.route
        ) {
            RegisterScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoute.Main.route
        ) {
            MainScreen(navHostController = navHostController)
        }
    }
}