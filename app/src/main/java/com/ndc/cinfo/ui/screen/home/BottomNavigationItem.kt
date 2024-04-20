package com.ndc.cinfo.ui.screen.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

data class BottomNavigationItem(
    val label: String,
    val unselectedIcon: Int,
    val selectedIcon: Int,
)
