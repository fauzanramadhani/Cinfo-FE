package com.ndc.cinfoadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ndc.cinfoadmin.ui.navigation.SetupNavHost
import com.ndc.core.theme.CinfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            CinfoTheme {
                SetupNavHost(navHostController = navHostController)
            }
        }
    }
}