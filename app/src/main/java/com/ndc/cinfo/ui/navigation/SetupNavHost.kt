package com.ndc.cinfo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ndc.cinfo.ui.screen.home.HomeScreen
import com.ndc.cinfo.ui.screen.login.LoginScreen
import com.ndc.cinfo.ui.screen.post.PostScreen
import com.ndc.cinfo.ui.screen.register.RegisterScreen
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.utils.SharedPreferencesManager

@Composable
fun SetupNavHost(
    navHostController: NavHostController
) {
    val firebaseAuth = Firebase.auth
    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = when {
            firebaseAuth.currentUser != null && SharedPreferencesManager(context).getString(
                SharedPref.USER_ID
            ).isNotEmpty() -> NavRoute.Home.route

            else -> NavRoute.Login.route
        },
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
            route = NavRoute.Home.route
        ) {
            HomeScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoute.Home.route
        ) {
            HomeScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoute.Post.route
        ) {
            PostScreen(navHostController = navHostController)
        }
    }
}