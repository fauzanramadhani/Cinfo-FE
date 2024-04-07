package com.ndc.cinfo.ui.screen.main

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ndc.cinfo.ui.component.button.PrimaryButton
import com.ndc.cinfo.ui.navigation.NavRoute

@Composable
fun MainScreen(
    navHostController: NavHostController
) {
    val firebaseAuth = Firebase.auth
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val ctx = LocalContext.current

        BackHandler {
            (ctx as Activity).finish()
        }

        Text(text = "Main Screen")
        Spacer(modifier = Modifier.padding(bottom = 12.dp))
        PrimaryButton(
            text = "Keluar"
        ) {
            firebaseAuth.signOut()
            val p = navHostController.currentBackStackEntry?.destination?.route
            Log.e("p", p.toString())
            navHostController.navigate(NavRoute.Login.route) {
                launchSingleTop = true
            }
        }
    }
}