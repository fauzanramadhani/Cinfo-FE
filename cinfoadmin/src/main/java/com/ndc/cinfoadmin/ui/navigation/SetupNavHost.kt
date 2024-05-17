package com.ndc.cinfoadmin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ndc.cinfoadmin.ui.feature.createpost.CreatePostScreen
import com.ndc.cinfoadmin.ui.feature.home.HomeScreen
import com.ndc.cinfoadmin.ui.feature.post.PostScreen

@Composable
fun SetupNavHost(
    navHostController: NavHostController
) {

    NavHost(
        navController = navHostController,
        startDestination = NavRoute.Home.route,
        route = NavRoute.Root.route
    ) {
        composable(
            route = NavRoute.Home.route
        ) {
            HomeScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoute.CreatePost.route
        ) {
            CreatePostScreen(navHostController = navHostController)
        }

        composable(
            route = NavRoute.Post.route
        ) {
            PostScreen(navHostController = navHostController)
        }
    }
}