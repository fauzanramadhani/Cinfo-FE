package com.ndc.cinfoadmin.ui.feature.post.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ndc.cinfoadmin.ui.feature.post.PostAction
import com.ndc.cinfoadmin.ui.feature.post.PostState
import com.ndc.core.R
import com.ndc.core.ui.component.item.BottomSheetItem
import com.ndc.core.ui.component.sheet.BaseBottomSheet
import com.ndc.core.ui.theme.CinfoTheme
import com.ndc.core.utils.toDateString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPostScreen(
    state: PostState = PostState(),
    onAction: (PostAction) -> Unit = {},
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    if (sheetState.isVisible) {
        BaseBottomSheet(
            modifier = Modifier
                .safeDrawingPadding(),
            sheetState = sheetState,
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                }
            },
            content = {
                Row(
                    modifier = Modifier
                        .padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 12.dp,
                            bottom = 24.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    BottomSheetItem(
                        icon = R.drawable.ic_edit,
                        text = "Ubah"
                    ) {
                        onAction(PostAction.OnChangeScreen(screen = 1))
                    }
                    BottomSheetItem(
                        icon = R.drawable.ic_trash,
                        text = "Hapus",
                        color = color.error
                    ) {
                        // TODO
                    }
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "",
                tint = color.primary,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onAction(PostAction.OnBackPressed)
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_dots_vertical),
                contentDescription = "",
                tint = color.primary,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        scope.launch {
                            sheetState.show()
                        }
                    }
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = state.title,
                style = typography.titleLarge,
                color = color.onBackground
            )
            Text(
                text = "Diterbitkan pada ${state.createdAt.toDateString()}",
                style = typography.bodyMedium,
                color = color.secondary
            )
        }
        Divider(
            thickness = 1.dp,
            color = color.outline
        )
        Text(
            text = state.description,
            style = typography.bodyMedium,
            color = color.onBackground
        )
    }
}

@Preview
@Composable
fun DetailPostScreenPreview() {
    CinfoTheme {
        DetailPostScreen(
            state = PostState(
                id = "1",
                title = "Pemberitahuan Jadwal Pelaksanaan UAS Semester 6",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                createdAt = System.currentTimeMillis()
            )
        )
    }
}