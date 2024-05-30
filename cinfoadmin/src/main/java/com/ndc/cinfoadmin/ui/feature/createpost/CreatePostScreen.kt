package com.ndc.cinfoadmin.ui.feature.createpost

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ndc.core.R
import com.ndc.core.ui.component.dialog.DialogLoading
import com.ndc.core.ui.component.textfield.BaseBasicTextField
import com.ndc.core.utils.Toast
import com.ndc.core.utils.toDateString

@Composable
fun CreatePostScreen(
    navHostController: NavHostController,
    createPostViewModel: CreatePostViewModel = hiltViewModel(),
) {
    val state by createPostViewModel.state.collectAsStateWithLifecycle(
        initialValue = CreatePostState()
    )
    val effect by createPostViewModel.onEffect.collectAsStateWithLifecycle(
        initialValue = CreatePostEffect.None
    )
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val ctx = LocalContext.current
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

    LaunchedEffect(effect) {
        when (effect) {
            CreatePostEffect.None -> {}
            CreatePostEffect.OnCreatePostSuccess -> navHostController.navigateUp()
        }
    }

    DialogLoading(
        visible = state.createPostLoading
    )

    state.createPostError?.let {
        Toast(ctx, it).short()
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
                        navHostController.navigateUp()
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "",
                tint = if (state.titleValue.isEmpty() || state.descriptionValue.isEmpty())
                    color.surfaceVariant else color.primary,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        enabled = state.titleValue.isNotEmpty() && state.descriptionValue.isNotEmpty() && !state.createPostLoading,
                        onClick = {
                            createPostViewModel.onAction(CreatePostAction.CreatePost)
                        }
                    )
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Buat Pengumuman Untuk",
                style = typography.titleLarge,
                color = color.onBackground
            )
            Text(
                text =
                if (state.room == null) "Universitas Cendekia Abditama"
                else "${state.room?.roomName} · ${state.room?.additional}",
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
                    createPostViewModel.onAction(CreatePostAction.OnTitleValueChange(it))
                },
                textStyle = typography.titleLarge,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Text(
                text = System.currentTimeMillis().toDateString(),
                style = typography.labelMedium,
                color = color.secondary
            )
        }
        BaseBasicTextField(
            label = "Ketik Pengumuman Disini",
            value = state.descriptionValue,
            onValueChange = {
                createPostViewModel.onAction(CreatePostAction.OnDescriptionValueChange(it))
            },
        )
    }
}