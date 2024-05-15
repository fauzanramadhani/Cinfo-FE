package com.ndc.cinfoadmin.ui.navigation

sealed class NavRoute (val route: String) {
    data object Root: NavRoute(route = "ROOT_NAVIGATION")
    data object Home: NavRoute(route = "HOME_SCREEN")
    data object CreatePost: NavRoute(route = "CREATE_POST")
    data object Post: NavRoute(route = "POST")
}