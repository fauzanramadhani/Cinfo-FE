package com.ndc.cinfo.ui.screen.main.content

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import com.ndc.cinfo.core.component.item.EventItem
import com.ndc.cinfo.data.event.datasource.remote.response.EventResponse
import com.ndc.cinfo.utils.toDateString

fun LazyListScope.mainScreen(
    navHostController: NavHostController
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