package com.ndc.cinfoadmin.ui.feature.eachroom.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ndc.cinfoadmin.ui.feature.eachroom.EachRoomAction
import com.ndc.cinfoadmin.ui.feature.eachroom.EachRoomState

@Composable
fun EachRoomPostScreen(
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    state: EachRoomState = EachRoomState(),
    onAction: (EachRoomAction) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Post Screen")
    }
}