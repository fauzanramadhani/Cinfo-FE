package com.ndc.cinfo.ui.screen.home

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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import com.ndc.cinfo.ui.navigation.NavRoute
import com.ndc.cinfo.ui.screen.home.content.AccountScreen
import com.ndc.cinfo.ui.screen.home.content.MainScreen
import com.ndc.cinfo.ui.screen.home.content.RoomScreen
import com.ndc.core.R
import com.ndc.core.ui.component.button.PrimaryButton
import com.ndc.core.ui.component.dialog.DialogChangeServerAddress
import com.ndc.core.ui.component.topbar.TopBarPrimaryLayout
import com.ndc.core.utils.Toast
import com.ndc.core.utils.rememberRestartActivity

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window
    val typography = MaterialTheme.typography
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            label = "Utama",
            unselectedIcon = R.drawable.ic_main,
            selectedIcon = R.drawable.ic_main_fill,
        ),
        BottomNavigationItem(
            label = "Kelas Saya",
            unselectedIcon = R.drawable.ic_room,
            selectedIcon = R.drawable.ic_room_fill
        ),
        BottomNavigationItem(
            label = "Account",
            unselectedIcon = R.drawable.ic_account,
            selectedIcon = R.drawable.ic_account_fill
        ),
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.onEffect.collectAsStateWithLifecycle(
        initialValue = HomeEffect.None
    )
    val onAction = viewModel::onAction
    var topBarVisibleMain by rememberSaveable {
        mutableStateOf(true)
    }
    var topBarVisibleRoom by rememberSaveable {
        mutableStateOf(true)
    }
    val mainListState = rememberLazyListState()
    var mainListLastVisibleIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val roomListState = rememberLazyListState()
    var roomListLastVisibleIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val restartApp = rememberRestartActivity(activity = (ctx as ComponentActivity))

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme

    when (effect) {
        HomeEffect.None -> {}
        HomeEffect.OnNavigateToDetailPostScreen -> navHostController.navigate(
            NavRoute.Post.route
        ) {
            launchSingleTop = true
        }

        is HomeEffect.OnShowToast -> Toast(ctx, (effect as HomeEffect.OnShowToast).message).short()
    }

    BackHandler {
        when {
            state.content != 0 -> onAction(HomeAction.OnContentChange(0))
            else -> (ctx as Activity).finish()
        }
    }

    LaunchedEffect(mainListState) {
        snapshotFlow { mainListState.firstVisibleItemIndex }
            .collect { newIndex ->
                if (newIndex > mainListLastVisibleIndex) {
                    topBarVisibleMain = false
                } else if (newIndex < mainListLastVisibleIndex) {
                    topBarVisibleMain = true
                }
                mainListLastVisibleIndex = newIndex
            }
    }

    LaunchedEffect(roomListState) {
        snapshotFlow { roomListState.firstVisibleItemIndex }
            .collect { newIndex ->
                if (newIndex > roomListLastVisibleIndex) {
                    topBarVisibleRoom = false
                } else if (newIndex < roomListLastVisibleIndex) {
                    topBarVisibleRoom = true
                }
                roomListLastVisibleIndex = newIndex
            }
    }

//    LaunchedEffect(state.room) {
//        state.room?.let {
//            onAction(HomeAction.OnObservePostPrivate(it.id))
//        }
//    }

    if (state.updateServerDialogShow) {
        DialogChangeServerAddress(
            modifier = Modifier.padding(24.dp),
            addressValue = state.updateServerTvValue,
            onAddressChange = {
                viewModel.onAction(HomeAction.OnUpdateServerTvChange(it))
            },
            onClearValue = {
                viewModel.onAction(HomeAction.OnUpdateServerTvChange(""))
            },
            onDismiss = {
                viewModel.onAction(HomeAction.OnUpdateServerDialogShowChange(false))
            },
            onConfirm = {
                viewModel.onAction(HomeAction.OnUpdateServer)
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
            AnimatedVisibility(
                visible = when (state.content) {
                    1 -> topBarVisibleRoom
                    2 -> true
                    else -> topBarVisibleMain
                },
                enter = fadeIn(initialAlpha = 1f),
                exit = fadeOut(targetAlpha = 0f)
            ) {
                when (state.content) {
                    1 -> TopBarPrimaryLayout {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = state.room?.roomName ?: "Anda tidak berada di dalam ruangan",
                                style = typography.bodyMedium,
                                color = color.onPrimary
                            )
                            state.room?.additional?.let {
                                Text(
                                    text = it,
                                    style = typography.labelLarge,
                                    color = color.onPrimary
                                )
                            }
                        }
                    }

                    2 -> TopBarPrimaryLayout {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Anda masuk sebagai:",
                                style = typography.bodyMedium,
                                color = color.onPrimary
                            )
                            Text(
                                text = state.firebaseUser?.email ?: "",
                                style = typography.labelLarge,
                                color = color.onPrimary,
                                modifier = Modifier
                                    .padding(top = 2.dp)
                            )
                            PrimaryButton(
                                text = "Keluar",
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = color.errorContainer,
                                    contentColor = color.error,
                                    disabledContainerColor = color.surfaceVariant,
                                ),
                                modifier = Modifier
                                    .padding(top = 24.dp)
                                    .fillMaxWidth()
                            ) {
                                onAction(HomeAction.OnLogout).also {
                                    navHostController.navigate(NavRoute.Login.route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }
                    }

                    else -> TopBarPrimaryLayout {
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
        },
        bottomBar = {
            Surface(
                shadowElevation = 12.dp,
            ) {
                BottomNavigationBar(
                    bottomNavigationItems = bottomNavigationItems,
                    selectedIndex = state.content,
                    onSelectedIndexChange = {
                        onAction(HomeAction.OnContentChange(it))
                    }
                )
            }
        }
    ) { paddingValues ->
        when (state.content) {
            1 -> RoomScreen(
                navHostController = navHostController,
                paddingValues = paddingValues,
                lazyListState = roomListState,
                state = state,
                onAction = onAction
            )

            2 -> AccountScreen(
                navHostController = navHostController,
                paddingValues = paddingValues,
            )

            else -> MainScreen(
                navHostController = navHostController,
                paddingValues = paddingValues,
                lazyListState = mainListState,
                state = state,
                onAction = onAction
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavigationItems: List<BottomNavigationItem>,
    selectedIndex: Int,
    onSelectedIndexChange: (index: Int) -> Unit
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    BottomAppBar(
        containerColor = Color.Transparent,
    ) {
        bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
            with(bottomNavigationItem) {
                val isSelected = selectedIndex == index
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        onSelectedIndexChange(index)
                    },
                    label = {
                        Text(
                            text = label,
                            style = typography.labelSmall
                        )
                    },
                    icon = {
                        Icon(
                            painterResource(
                                id = if (isSelected) selectedIcon
                                else unselectedIcon
                            ),
                            contentDescription = ""
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = color.primary,
                        unselectedIconColor = color.secondary,
                        selectedTextColor = color.primary,
                        unselectedTextColor = color.secondary,
                        indicatorColor = color.primaryContainer
                    )
                )
            }
        }
    }
}