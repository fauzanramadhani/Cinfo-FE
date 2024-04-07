package com.ndc.cinfo.ui.screen.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.cinfo.R
import com.ndc.cinfo.ui.screen.main.content.AccountScreen
import com.ndc.cinfo.ui.screen.main.content.MainScreen
import com.ndc.cinfo.ui.screen.main.content.RoomScreen

@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            label = "Utama",
            unselectedIcon = R.drawable.ic_main,
            selectedIcon = R.drawable.ic_main_fill,
            content = { paddingValues ->
                MainScreen(
                    navHostController = navHostController,
                    paddingValues = paddingValues
                )
            }
        ),
        BottomNavigationItem(
            label = "Kelas Saya",
            unselectedIcon = R.drawable.ic_room,
            selectedIcon = R.drawable.ic_room_fill,
            content = { paddingValues ->
                RoomScreen(
                    navHostController = navHostController,
                    paddingValues = paddingValues
                )
            }
        ),
        BottomNavigationItem(
            label = "Account",
            unselectedIcon = R.drawable.ic_account,
            selectedIcon = R.drawable.ic_account_fill,
            content = { paddingValues ->
                AccountScreen(
                    navHostController = navHostController,
                    paddingValues = paddingValues
                )
            }
        ),
    )
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    BackHandler {
        (ctx as Activity).finish()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color.background)
            .safeDrawingPadding(),
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
        bottomNavigationItems[selectedIndex].content.invoke(paddingValues)
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