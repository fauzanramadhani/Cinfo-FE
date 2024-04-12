package com.ndc.cinfo.ui.screen.main.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.core.component.item.EventItem
import com.ndc.cinfo.data.event.datasource.remote.response.EventResponse
import com.ndc.cinfo.utils.toDateString

@Composable
fun MainScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    val dummyEvent = remember {
        mutableStateListOf<EventResponse>()
    }
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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