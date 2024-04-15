package com.ndc.cinfo.ui.screen.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

data class BottomNavigationItem(
    val label: String,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val topBar: @Composable () -> Unit,
    val content: @Composable (
        paddingValues: PaddingValues,
        topBarVisibility: MutableState<Boolean>
    ) -> Unit,
)
