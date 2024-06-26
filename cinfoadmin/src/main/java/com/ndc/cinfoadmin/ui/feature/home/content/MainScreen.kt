package com.ndc.cinfoadmin.ui.feature.home.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ndc.cinfoadmin.ui.feature.home.HomeAction
import com.ndc.cinfoadmin.ui.feature.home.HomeState
import com.ndc.core.R
import com.ndc.core.ui.component.button.PrimaryButton
import com.ndc.core.ui.component.item.AnnouncementItem
import com.ndc.core.utils.MSocketException
import com.ndc.core.utils.toDateString

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    state: HomeState,
    onAction: (HomeAction) -> Unit = {},
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
            top = 71.dp + 16.dp // must be a static value
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        when {
            state.errorPostGlobalMessage != null -> item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.server_failure_illustration),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.padding(bottom = 16.dp))
                    Text(
                        text = when (state.errorPostGlobalMessage) {
                            is MSocketException.EmptyServerAddress -> "Alamat server masih kosong"
                            else -> state.errorPostGlobalMessage.message.toString()
                        },
                        style = typography.bodyMedium,
                        color = color.onBackground
                    )

                    Text(
                        text = when (state.errorPostGlobalMessage) {
                            is MSocketException.EmptyServerAddress -> "Silahkan perbarui alamat server"
                            else -> state.errorPostGlobalMessage.message.toString()
                        },
                        style = typography.bodyMedium,
                        color = color.onBackground
                    )
                    Spacer(modifier = Modifier.padding(bottom = 24.dp))
                    PrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Perbarui"
                    ) {
                        onAction(HomeAction.OnUpdateServerDialogShowChange(show = true))
                    }
                }
            }

            state.postGlobalMap.isEmpty() ->
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
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
                    items = state.postGlobalMap.values.toList(),
                    key = { event ->
                        event.id
                    }
                ) {
                    AnnouncementItem(
                        title = it.title,
                        createdAt = it.createdAt.toDateString("dd MMMM yyyy")
                    ) {
                        onAction(
                            HomeAction.OnItemClicked(
                                id = it.id,
                                title = it.title,
                                description = it.description,
                                createdAt = it.createdAt
                            )
                        )
                    }
                }
        }
    }
}