package com.ndc.cinfoadmin.ui.screen.home

import android.app.Activity
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ndc.cinfoadmin.ui.screen.home.content.MainScreen
import com.ndc.cinfoadmin.ui.screen.home.content.RoomScreen
import com.ndc.core.R
import com.ndc.core.component.topbar.TopBarPrimaryLayout


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
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            label = "Utama",
            unselectedIcon = R.drawable.ic_main,
            selectedIcon = R.drawable.ic_main_fill,
            content = { paddingValues, topBarVisibility ->
                MainScreen(
                    navHostController = navHostController,
                    paddingValues = paddingValues,
                    topBarVisibility = topBarVisibility
                )
            },
            topBar = {
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
        ),
        BottomNavigationItem(
            label = "Kelas Saya",
            unselectedIcon = R.drawable.ic_room,
            selectedIcon = R.drawable.ic_room_fill,
            content = { paddingValues, topBarVisibility ->
                RoomScreen(
                    navHostController = navHostController,
                    paddingValues = paddingValues,
                    topBarVisibility = topBarVisibility
                )
            },
            topBar = {
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
        )
    )
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val topBarVisibleMain = rememberSaveable {
        mutableStateOf(true)
    }
    val topBarVisibleRoom = rememberSaveable {
        mutableStateOf(true)
    }

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme

    BackHandler {
        when {
            selectedIndex != 0 -> selectedIndex = 0
            else -> (ctx as Activity).finish()
        }
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
                visible = when (selectedIndex) {
                    0 -> topBarVisibleMain.value
                    else -> topBarVisibleRoom.value
                },
                enter = fadeIn(initialAlpha = 1f),
                exit = fadeOut(targetAlpha = 0f)
            ) {
                bottomNavigationItems[selectedIndex].topBar.invoke()
            }
        },
        bottomBar = {
            Surface(
                shadowElevation = 12.dp,
            ) {
                BottomNavigationBar(
                    bottomNavigationItems = bottomNavigationItems,
                    selectedIndex = selectedIndex,
                    onSelectedIndexChange = {
                        selectedIndex = it
                    }
                )
            }
        }
    ) { paddingValues ->
        bottomNavigationItems[selectedIndex].content.invoke(
            paddingValues,
            when (selectedIndex) {
                0 -> topBarVisibleMain
                else -> topBarVisibleRoom
            }
        )
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