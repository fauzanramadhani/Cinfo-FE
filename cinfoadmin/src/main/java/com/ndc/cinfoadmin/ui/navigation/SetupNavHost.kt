package com.ndc.cinfoadmin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ndc.cinfoadmin.ui.feature.createpost.CreatePostScreen
import com.ndc.cinfoadmin.ui.feature.createroom.CreateRoom
import com.ndc.cinfoadmin.ui.feature.eachroom.EachRoomScreen
import com.ndc.cinfoadmin.ui.feature.editroom.EditRoom
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

        composable(
            route = NavRoute.CreateRoom.route
        ) {
            CreateRoom(navHostController = navHostController)
        }

        composable(
            route = NavRoute.EditRoom.route
        ) {
            EditRoom(navHostController = navHostController)
        }

        composable(
            route = NavRoute.EachRoom.route
        ) {
            EachRoomScreen(navHostController = navHostController)
        }
    }
}