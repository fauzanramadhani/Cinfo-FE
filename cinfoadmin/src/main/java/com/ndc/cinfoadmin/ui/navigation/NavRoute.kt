package com.ndc.cinfoadmin.ui.navigation

sealed class NavRoute(val route: String) {
    data object Root : NavRoute(route = "ROOT_NAVIGATION")
    data object Home : NavRoute(route = "HOME_SCREEN")
    data object CreatePost : NavRoute(route = "CREATE_POST")
    data object Post : NavRoute(route = "POST")
    data object CreateRoom : NavRoute(route = "CREATE_ROOM")
    data object EditRoom : NavRoute(route = "EDIT_ROOM")
    data object EachRoom : NavRoute(route = "EACH_ROOM")
}