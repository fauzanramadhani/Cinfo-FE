package com.ndc.cinfo.ui.screen.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.core.component.item.EventItem
import com.ndc.cinfo.data.event.datasource.remote.response.EventResponse
import com.ndc.cinfo.utils.toDateString

@Composable
fun MainScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    topBarVisibility: MutableState<Boolean>
) {
    val dummyEvent = mutableListOf<EventResponse>()
    for (i in 1..10) {
        dummyEvent.add(
            EventResponse(
                id = "EVENT_$i",
                title =
                if (i % 2 == 0) "Pemberitahuan Liburan Idul Adha"
                else "Pemeberitahuan pelunasan SPP menjelang UTS Pemeberitahuan pelunasan SPP menjelang UTS",
                createdAt = 1727966481000
            )
        )
    }
    val lazyListState = rememberLazyListState()
    var lastVisibleIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { newIndex ->
                if (newIndex > lastVisibleIndex) {
                    topBarVisibility.value = false
                } else if (newIndex < lastVisibleIndex) {
                    topBarVisibility.value = true
                }
                lastVisibleIndex = newIndex
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp,
            top = 71.dp + 16.dp // must be a static value
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = dummyEvent,
            key = { event ->
                event.id
            }
        ) {
            EventItem(
                title = it.title,
                createdAt = it.createdAt.toDateString("dd MMMM yyyy")
            )
        }
    }
}