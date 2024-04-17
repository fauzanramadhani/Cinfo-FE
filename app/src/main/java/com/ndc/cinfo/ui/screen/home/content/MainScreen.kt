package com.ndc.cinfo.ui.screen.home.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.R
import com.ndc.cinfo.ui.navigation.NavRoute
import com.ndc.cinfo.utils.toDateString
import com.ndc.core.component.item.AnnouncementItem
import com.ndc.core.data.event.datasource.remote.response.AnnouncementResponse

@Composable
fun MainScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    topBarVisibility: MutableState<Boolean>
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val dummyAnnouncement = remember {
        mutableStateListOf<AnnouncementResponse>()
    }
    for (i in 1..10) {
        dummyAnnouncement.add(
            AnnouncementResponse(
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
        when {
            dummyAnnouncement.isEmpty() ->
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
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

            else ->
                items(
                    items = dummyAnnouncement,
                    key = { event ->
                        event.id
                    }
                ) {
                    AnnouncementItem(
                        title = it.title,
                        createdAt = it.createdAt.toDateString("dd MMMM yyyy")
                    ) {
                        navHostController.navigate(NavRoute.DetailAnnouncement.route) {
                            launchSingleTop = true
                        }
                    }
                }
        }
    }
}