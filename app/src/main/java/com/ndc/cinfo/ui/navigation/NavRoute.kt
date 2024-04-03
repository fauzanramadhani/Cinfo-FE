package com.ndc.cinfo.ui.navigation

sealed class NavRoute (val route: String) {
    data object ROOT: NavRoute(route = "ROOT_NAVIGATION")
    data object LOGIN: NavRoute(route = "LOGIN_SCREEN")
    data object REGISTER: NavRoute(route = "REGISTER_SCREEN")
}