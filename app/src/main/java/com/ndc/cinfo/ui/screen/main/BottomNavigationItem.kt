package com.ndc.cinfo.ui.screen.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

data class BottomNavigationItem(
    val label: String,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val topBar: @Composable () -> Unit,
    val content: @Composable (
        paddingValues: PaddingValues
    ) -> Unit
)
