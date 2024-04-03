package com.ndc.cinfo.ui.navigation

sealed class NavRoute (val route: String) {
    data object Root: NavRoute(route = "ROOT_NAVIGATION")
    data object Login: NavRoute(route = "LOGIN_SCREEN")
    data object Register: NavRoute(route = "REGISTER_SCREEN")
    data object Main: NavRoute(route = "MAIN_SCREEN")
}