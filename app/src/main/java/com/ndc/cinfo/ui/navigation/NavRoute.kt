package com.ndc.cinfo.ui.navigation

sealed class NavRoute (val route: String) {
    data object Root: NavRoute(route = "ROOT_NAVIGATION")
    data object Login: NavRoute(route = "LOGIN_SCREEN")
    data object Register: NavRoute(route = "REGISTER_SCREEN")
    data object Home: NavRoute(route = "HOME_SCREEN")
    data object DetailAnnouncement: NavRoute(route = "DETAIL_ANNOUNCEMENT")
}