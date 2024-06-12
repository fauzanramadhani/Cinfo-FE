package com.ndc.cinfo.ui.screen.post

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ndc.core.R
import com.ndc.core.ui.component.dialog.DialogLoading
import com.ndc.core.utils.Toast
import com.ndc.core.utils.toDateString

@Composable
fun PostScreen(
    navHostController: NavHostController,
    postViewModel: PostViewModel = hiltViewModel()
) {
    val ctx = LocalContext.current
    val state by postViewModel.state.collectAsStateWithLifecycle(
        initialValue = PostState()
    )
    val effect by postViewModel.onEffect.collectAsStateWithLifecycle(initialValue = PostEffect.None)
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val scope = rememberCoroutineScope()

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

    DialogLoading(
        visible = state.loading
    )
    state.error?.let {
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

    LaunchedEffect(effect) {
        when (effect) {
            PostEffect.None -> {}
            PostEffect.OnNavigateUp -> navHostController.navigateUp()
        }
    }
}