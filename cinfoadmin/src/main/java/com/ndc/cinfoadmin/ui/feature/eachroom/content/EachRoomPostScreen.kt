package com.ndc.cinfoadmin.ui.feature.eachroom.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ndc.cinfoadmin.ui.feature.eachroom.EachRoomAction
import com.ndc.cinfoadmin.ui.feature.eachroom.EachRoomState
import com.ndc.core.R
import com.ndc.core.ui.component.item.AnnouncementItem
import com.ndc.core.ui.component.shimmer.shimmerBrush
import com.ndc.core.utils.toDateString

@Composable
fun EachRoomPostScreen(
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    state: EachRoomState = EachRoomState(),
    onAction: (EachRoomAction) -> Unit = {},
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val ctx = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp,
            top = paddingValues.calculateTopPadding() + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        when {
            state.loadingObserve && state.postPrivate == null -> items(3) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .fillMaxWidth()
                        .height(92.dp)
                        .background(shimmerBrush())
                )
            }

            state.postPrivate != null && state.postPrivate.isEmpty() ->
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.empty_illustration),
                            contentDescription = ""
                        )
                        Text(
                            text = "Ups... Sepertinya belum ada pengumuman",
                            style = typography.bodyMedium,
                            color = color.onBackground
                        )
                    }
                }

            else -> state.postPrivate?.let {
                items(
                    items = state.postPrivate.values.toList(),
                    key = { event ->
                        event.id
                    }
                ) {
                    AnnouncementItem(
                        title = it.title,
                        createdAt = it.createdAt.toDateString("dd MMMM yyyy")
                    ) {
                        onAction(
                            EachRoomAction.OnPostPrivateClicked(it)
                        )
                    }
                }
            }
        }
    }
}