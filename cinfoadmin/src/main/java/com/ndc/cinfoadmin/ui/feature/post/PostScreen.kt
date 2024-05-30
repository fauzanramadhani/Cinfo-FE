package com.ndc.cinfoadmin.ui.feature.post

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ndc.cinfoadmin.ui.feature.post.screen.DetailPostScreen
import com.ndc.cinfoadmin.ui.feature.post.screen.EditPostScreen
import com.ndc.core.ui.component.dialog.DialogLoading
import com.ndc.core.utils.Toast

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

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

    DialogLoading(
        visible = state.loading
    )
    state.error?.let {
        Toast(ctx, it).short()
    }
    when (state.currentScreen) {
        0 -> DetailPostScreen(
            state = state,
            onAction = postViewModel::onAction,
        )

        1 -> EditPostScreen(
            state = state,
            onAction = postViewModel::onAction
        )
    }

    LaunchedEffect(effect) {
        when (effect) {
            PostEffect.None -> {}
            PostEffect.OnNavigateUp -> navHostController.navigateUp()
        }
    }

    BackHandler {
        postViewModel.onAction(PostAction.OnBackPressed)
    }
}