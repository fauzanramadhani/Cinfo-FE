package com.ndc.cinfoadmin.ui.feature.home.content

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ndc.cinfoadmin.ui.feature.home.HomeAction
import com.ndc.cinfoadmin.ui.feature.home.HomeState
import com.ndc.core.R
import com.ndc.core.ui.component.button.PrimaryButton
import com.ndc.core.ui.component.item.AnnouncementItem
import com.ndc.core.ui.component.shimmer.shimmerBrush
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

            state.loading && state.postGlobalResponseMap == null -> items(3) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .fillMaxWidth()
                        .height(92.dp)
                        .background(shimmerBrush())
                )
            }

            state.postGlobalResponseMap != null && state.postGlobalResponseMap.isEmpty() ->
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

            else -> state.postGlobalResponseMap?.let {
                items(
                    items = state.postGlobalResponseMap.values.toList(),
                    key = { event ->
                        event.id
                    }
                ) {
                    AnnouncementItem(
                        title = it.title,
                        createdAt = it.createdAt.toDateString("dd MMMM yyyy")
                    ) {
                        onAction(HomeAction.OnSavePostTypeGlobal)
                        onAction(HomeAction.OnItemPostGlobalClicked(it))
                    }
                }
            }
        }

        if (state.postGlobalResponseMap.isNullOrEmpty()) {
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