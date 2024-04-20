package com.ndc.cinfoadmin.ui.screen.home.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun RoomScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    lazyListState: LazyListState
) {
    Text(text = "Room Screen")
}