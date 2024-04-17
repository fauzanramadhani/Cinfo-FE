package com.ndc.cinfoadmin.ui.navigation

sealed class NavRoute (val route: String) {
    data object Root: NavRoute(route = "ROOT_NAVIGATION")
    data object HOME: NavRoute(route = "HOME_SCREEN")
    data object DetailAnnouncement: NavRoute(route = "DETAIL_ANNOUNCEMENT")
}