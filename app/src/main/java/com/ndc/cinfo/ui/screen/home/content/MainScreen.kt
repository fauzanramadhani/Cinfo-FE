package com.ndc.cinfo.ui.screen.home.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.ui.screen.home.HomeAction
import com.ndc.cinfo.ui.screen.home.HomeState
import com.ndc.core.R
import com.ndc.core.ui.component.button.PrimaryButton
import com.ndc.core.ui.component.item.AnnouncementItem
import com.ndc.core.ui.component.shimmer.shimmerBrush
import com.ndc.core.utils.MSocketException
import com.ndc.core.utils.toDateString

@Composable
fun MainScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
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
            state.error != null -> item {
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
                        text = when (state.error) {
                            is MSocketException.EmptyServerAddress -> "Alamat server masih kosong"
                            else -> state.error.message.toString()
                        },
                        style = typography.bodyMedium,
                        color = color.onBackground
                    )
                    Text(
                        text = "Silahkan perbarui alamat server",
                        style = typography.bodyMedium,
                        color = color.onBackground
                    )
                }
            }

            state.loading && state.postGlobalMap == null -> items(3) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .fillMaxWidth()
                        .height(92.dp)
                        .background(shimmerBrush())
                )
            }

            state.postGlobalMap != null && state.postGlobalMap.isEmpty() ->
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

            else -> state.postGlobalMap?.let {
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
                        onAction(HomeAction.OnItemPostGlobalClicked(it))
                    }
                }
            }
        }

        if (state.postGlobalMap.isNullOrEmpty()) {
            item {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Perbarui Alamat Server"
                ) {
                    onAction(HomeAction.OnUpdateServerDialogShowChange(show = true))
                }
            }
        }
    }
}