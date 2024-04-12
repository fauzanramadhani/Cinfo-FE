package com.ndc.cinfo.ui.screen.main

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable

data class BottomNavigationItem(
    val label: String,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val topBar: @Composable () -> Unit,
    val lazyListState: LazyListState,
    val content: LazyListScope.() -> Unit
)
