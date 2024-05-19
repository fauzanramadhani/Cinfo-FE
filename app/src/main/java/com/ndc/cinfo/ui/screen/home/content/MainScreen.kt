package com.ndc.cinfo.ui.screen.home.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.ui.navigation.NavRoute
import com.ndc.core.R
import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.ui.component.item.AnnouncementItem
import com.ndc.core.utils.toDateString

@Composable
fun MainScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    announcementList: List<PostGlobalResponse>
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

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
            announcementList.isEmpty() ->
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
                    items = announcementList,
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