package com.ndc.cinfoadmin.ui.feature.home

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ndc.cinfoadmin.ui.feature.home.content.MainScreen
import com.ndc.cinfoadmin.ui.feature.home.content.RoomScreen
import com.ndc.cinfoadmin.ui.navigation.NavRoute
import com.ndc.core.R
import com.ndc.core.ui.component.bar.BottomNavigationBar
import com.ndc.core.ui.component.dialog.DialogChangeServerAddress
import com.ndc.core.ui.component.topbar.TopBarPrimaryLayout
import com.ndc.core.utils.rememberRestartActivity


@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window
    val typography = MaterialTheme.typography

    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    var topBarVisibleMain by rememberSaveable {
        mutableStateOf(true)
    }
    var topBarVisibleRoom by rememberSaveable {
        mutableStateOf(true)
    }
    val mainListState = rememberLazyListState()
    val roomListState = rememberLazyListState()
    val state by homeScreenViewModel.state.collectAsStateWithLifecycle(
        initialValue = HomeState()
    )
    val effect by homeScreenViewModel.onEffect.collectAsStateWithLifecycle(initialValue = HomeEffect.None)
    val restartApp = rememberRestartActivity(activity = (ctx as ComponentActivity))

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme

    when (effect) {
        HomeEffect.None -> {}
        HomeEffect.OnItemPostClicked -> navHostController.navigate(
            NavRoute.Post.route
        ) {
            launchSingleTop = true
        }

        HomeEffect.OnItemRoomClicked -> navHostController.navigate(
            NavRoute.EachRoom.route
        ) {
            launchSingleTop = true
        }
    }

    BackHandler {
        when {
            selectedIndex != 0 -> selectedIndex = 0
            else -> (ctx as Activity).finish()
        }
    }

    LaunchedEffect(mainListState) {
        snapshotFlow { mainListState.firstVisibleItemIndex }
            .collect { newIndex ->
                topBarVisibleMain = newIndex <= 0
            }
    }

    LaunchedEffect(roomListState) {
        snapshotFlow { roomListState.firstVisibleItemIndex }
            .collect { newIndex ->
                topBarVisibleRoom = newIndex <= 0
            }
    }

    if (state.updateServerDialogShow) {
        DialogChangeServerAddress(
            modifier = Modifier.padding(24.dp),
            addressValue = state.updateServerTvValue,
            onAddressChange = {
                homeScreenViewModel.onAction(HomeAction.OnUpdateServerTvChange(it))
            },
            onClearValue = {
                homeScreenViewModel.onAction(HomeAction.OnUpdateServerTvChange(""))
            },
            onDismiss = {
                homeScreenViewModel.onAction(HomeAction.OnUpdateServerDialogShowChange(false))
            },
            onConfirm = {
                homeScreenViewModel.onAction(HomeAction.OnUpdateServer)
                restartApp()
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color.primary)
            .statusBarsPadding()
            .background(color = color.background)
            .safeDrawingPadding(),
        topBar = {
            when (selectedIndex) {
                0 -> {
                    AnimatedVisibility(
                        visible = topBarVisibleMain,
                        enter = fadeIn(initialAlpha = 1f),
                        exit = fadeOut(targetAlpha = 0f)
                    ) {
                        TopBarPrimaryLayout {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = "Dashboard Mahasiswa",
                                    style = typography.bodyMedium,
                                    color = color.onPrimary
                                )
                                Text(
                                    text = "Per-Jurusan",
                                    style = typography.labelLarge,
                                    color = color.onPrimary
                                )
                            }
                        }
                    }
                }

                1 -> {
                    AnimatedVisibility(
                        visible = topBarVisibleRoom,
                        enter = fadeIn(initialAlpha = 1f),
                        exit = fadeOut(targetAlpha = 0f)
                    ) {
                        TopBarPrimaryLayout {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = "Dashboard Mahasiswa",
                                        style = typography.bodyMedium,
                                        color = color.onPrimary
                                    )
                                    Text(
                                        text = "Universitas Cendekia Abditama",
                                        style = typography.labelLarge,
                                        color = color.onPrimary
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.uca_logo),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .background(Color.White, shape = CircleShape)
                                        .size(36.dp)
                                )
                            }

                        }
                    }
                }
            }
        },
        floatingActionButton = {
            when (selectedIndex) {
                0 -> FloatingActionButton(
                    modifier = Modifier.padding(12.dp),
                    contentColor = color.onPrimaryContainer,
                    containerColor = color.primaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navHostController.navigate(NavRoute.CreatePost.route) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_post),
                        contentDescription = ""
                    )
                }

                1 -> FloatingActionButton(
                    modifier = Modifier.padding(12.dp),
                    contentColor = color.onPrimaryContainer,
                    containerColor = color.primaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navHostController.navigate(NavRoute.CreateRoom.route) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_room),
                        contentDescription = ""
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                shadowElevation = 12.dp,
            ) {
                BottomNavigationBar(
                    bottomNavigationItems = state.bottomNavigationItems,
                    selectedIndex = selectedIndex,
                    onSelectedIndexChange = {
                        selectedIndex = it
                    }
                )
            }
        }
    ) { paddingValues ->
        when (selectedIndex) {
            1 -> RoomScreen(
                paddingValues = paddingValues,
                lazyListState = roomListState,
                state = state,
                onAction = homeScreenViewModel::onAction
            )

            else -> MainScreen(
                paddingValues = paddingValues,
                lazyListState = mainListState,
                state = state,
                onAction = homeScreenViewModel::onAction
            )
        }
    }
}