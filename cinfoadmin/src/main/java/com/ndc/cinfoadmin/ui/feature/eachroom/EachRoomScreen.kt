package com.ndc.cinfoadmin.ui.feature.eachroom

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ndc.cinfoadmin.ui.feature.eachroom.content.EachRoomMemberScreen
import com.ndc.cinfoadmin.ui.feature.eachroom.content.EachRoomPostScreen
import com.ndc.cinfoadmin.ui.navigation.NavRoute
import com.ndc.core.R
import com.ndc.core.ui.component.bar.BottomNavigationBar
import com.ndc.core.ui.component.topbar.TopBarPrimaryLayout

@Composable
fun EachRoomScreen(
    navHostController: NavHostController,
    eachRoomViewModel: EachRoomViewModel = hiltViewModel(),
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val postListState = rememberLazyListState()
    val memberListState = rememberLazyListState()

    val state by eachRoomViewModel.state.collectAsStateWithLifecycle(
        initialValue = EachRoomState()
    )
    val effect by eachRoomViewModel.onEffect.collectAsStateWithLifecycle(initialValue = EachRoomEffect.None)
    val onAction = eachRoomViewModel::onAction

    when (effect) {
        EachRoomEffect.None -> {}
        EachRoomEffect.OnPostItemClicked -> navHostController.navigate(
            NavRoute.Post.route
        ) {
            launchSingleTop = true
        }
    }

    BackHandler {
        when {
            state.currentContent != 0 -> onAction(EachRoomAction.OnScreenChange(0))
            else -> navHostController.navigateUp()
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
            TopBarPrimaryLayout {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = state.room?.roomName ?: "",
                            style = typography.bodyMedium,
                            color = color.onPrimary
                        )
                        Text(
                            text = state.room?.additional ?: "",
                            style = typography.labelLarge,
                            color = color.onPrimary
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dots_vertical),
                        contentDescription = "",
                        tint = color.onPrimary,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

            }
        },
        floatingActionButton = {
            when (state.currentContent) {
                0 -> FloatingActionButton(
                    modifier = Modifier.padding(12.dp),
                    contentColor = color.onPrimaryContainer,
                    containerColor = color.primaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
//                        navHostController.navigate(NavRoute.CreatePost.route) {
//                            launchSingleTop = true
//                        }
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
                        // TODO
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_user_add),
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
                    selectedIndex = state.currentContent,
                    onSelectedIndexChange = {
                        onAction(EachRoomAction.OnScreenChange(it))
                    }
                )
            }
        }
    ) { paddingValues ->
        when (state.currentContent) {
            1 -> EachRoomMemberScreen(
                paddingValues = paddingValues,
                lazyListState = memberListState,
                state = state,
                onAction = onAction
            )

            else -> EachRoomPostScreen(
                paddingValues = paddingValues,
                lazyListState = postListState,
                state = state,
                onAction = onAction
            )
        }
    }
}