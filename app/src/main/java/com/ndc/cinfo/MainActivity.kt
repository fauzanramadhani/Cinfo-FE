package com.ndc.cinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ndc.cinfo.ui.navigation.SetupNavHost
import com.ndc.core.ui.theme.CinfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CinfoTheme {
                val navHostController = rememberNavController()
                SetupNavHost(navHostController = navHostController)
            }
        }
    }
}