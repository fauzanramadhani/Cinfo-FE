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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ndc.cinfoadmin.ui.feature.post.PostAction
import com.ndc.cinfoadmin.ui.feature.post.PostState
import com.ndc.core.R
import com.ndc.core.ui.component.textfield.BaseBasicTextField
import com.ndc.core.utils.toDateString

@Composable
fun EditPostScreen(
    state: PostState = PostState(),
    onAction: (PostAction) -> Unit = {},
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    fun isEmptyOrSameValue(): Boolean {
        return with(state) {
            val isEmpty = titleValue.isEmpty() || descriptionValue.isEmpty()
            val isSameValue = title == titleValue && description == descriptionValue
            isEmpty || isSameValue
        }
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
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "",
                tint = if (isEmptyOrSameValue())
                    color.surfaceVariant else color.primary,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        enabled = state.title.isNotEmpty() && state.description.isNotEmpty(),
                        onClick = {
                            onAction(PostAction.OnEditPost)
                        }
                    )
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Ubah Pengumuman Untuk",
                style = typography.titleLarge,
                color = color.onBackground
            )

            Text(
                text = if (state.room != null) {
                    "${state.room.roomName} · ${state.room.additional}"
                } else {
                    "Universitas Cendekia Abditama"
                },
                style = typography.labelLarge,
                color = color.primary
            )

        }
        Divider(
            thickness = 1.dp,
            color = color.outline
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BaseBasicTextField(
                label = "Ketik Judul",
                value = state.titleValue,
                onValueChange = {
                    onAction(PostAction.OnTitleValueChange(it))
                },
                textStyle = typography.titleLarge,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Text(
                text = state.createdAt.toDateString(),
                style = typography.labelMedium,
                color = color.secondary
            )
        }
        BaseBasicTextField(
            label = "Ketik Pengumuman Disini",
            value = state.descriptionValue,
            onValueChange = {
                onAction(PostAction.OnDescriptionValueChange(it))
            },
        )
    }
}