package com.ndc.cinfoadmin.ui.screen.home.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController

@Composable
fun RoomScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    topBarVisibility: MutableState<Boolean>
) {
    Text(text = "Room Screen")
}