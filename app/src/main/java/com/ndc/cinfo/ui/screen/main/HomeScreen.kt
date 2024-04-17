package com.ndc.cinfo.ui.screen.main

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.ndc.cinfo.R
import com.ndc.cinfo.core.component.button.PrimaryButton
import com.ndc.cinfo.core.component.topbar.TopBarPrimaryLayout
import com.ndc.cinfo.ui.navigation.NavRoute
import com.ndc.cinfo.ui.screen.main.content.AccountScreen
import com.ndc.cinfo.ui.screen.main.content.MainScreen
import com.ndc.cinfo.ui.screen.main.content.RoomScreen

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
                            text = "Teknik Informatika",
                            style = typography.bodyMedium,
                            color = color.onPrimary
                        )
                        Text(
                            text = "Angkatan 2020",
                            style = typography.labelLarge,
                            color = color.onPrimary
                        )
                    }
                }
            }
        ),
        BottomNavigationItem(
            label = "Account",
            unselectedIcon = R.drawable.ic_account,
            selectedIcon = R.drawable.ic_account_fill,
            content = { paddingValues, topBarVisibility ->
                AccountScreen(
                    navHostController = navHostController,
                    paddingValues = paddingValues,
                    topBarVisibility = topBarVisibility
                )
            },
            topBar = {
                TopBarPrimaryLayout {
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
                            text = homeScreenViewModel.firebaseUser()?.email ?: "",
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
                            homeScreenViewModel.logout()
                            navHostController.navigate(NavRoute.Login.route) {
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        ),
    )
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val topBarVisible = rememberSaveable {
        mutableStateOf(true)
    }

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme

    BackHandler {
        (ctx as Activity).finish()
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
                visible = topBarVisible.value,
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
            topBarVisible
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