package com.ndc.cinfo.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.ui.component.button.PrimaryButton

@Composable
fun MainScreen(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(text = "Main Screen")
        PrimaryButton(
            text = "Keluar"
        )
    }
}