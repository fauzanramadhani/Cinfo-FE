package com.ndc.cinfo.ui.screen.main.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

fun LazyListScope.accountScreen(
    navHostController: NavHostController,
) {
    item {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Account Screen")
        }
    }
}