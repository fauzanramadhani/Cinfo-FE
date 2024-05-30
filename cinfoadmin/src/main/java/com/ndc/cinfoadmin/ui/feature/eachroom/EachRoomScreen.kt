package com.ndc.cinfoadmin.ui.feature.eachroom

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ndc.cinfoadmin.ui.feature.eachroom.content.EachRoomMemberScreen
import com.ndc.cinfoadmin.ui.feature.eachroom.content.EachRoomPostScreen
import com.ndc.cinfoadmin.ui.navigation.NavRoute
import com.ndc.core.R
import com.ndc.core.ui.component.bar.BottomNavigationBar
import com.ndc.core.ui.component.dialog.DialogLoading
import com.ndc.core.ui.component.item.BottomSheetItem
import com.ndc.core.ui.component.sheet.BaseBottomSheet
import com.ndc.core.ui.component.textfield.BaseBasicTextField
import com.ndc.core.ui.component.topbar.TopBarPrimaryLayout
import com.ndc.core.utils.Toast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    DialogLoading(
        visible = state.loadingEmit
    )

    LaunchedEffect(navHostController.previousBackStackEntry?.destination?.route) {
        onAction(EachRoomAction.OnReloadRoomCache)
    }

    LaunchedEffect(effect) {
        when (effect) {
            EachRoomEffect.None -> {}
            EachRoomEffect.OnNavigateToPost -> navHostController.navigate(
                NavRoute.Post.route
            ) {
                launchSingleTop = true
            }

            EachRoomEffect.OnDeleteRoomSuccess -> {
                sheetState.hide()
                navHostController.navigateUp()
            }

            EachRoomEffect.OnDeleteMemberSuccess -> sheetState.hide()
            EachRoomEffect.OnEmitMemberSuccess -> sheetState.hide()
            is EachRoomEffect.OnShowToast -> Toast(
                ctx,
                (effect as EachRoomEffect.OnShowToast).message
            ).short()
        }
    }

    BackHandler {
        when {
            state.currentContent != 0 -> onAction(EachRoomAction.OnScreenChange(0))
            else -> navHostController.navigateUp()
        }
    }

    if (sheetState.isVisible) {
        BaseBottomSheet(
            modifier = Modifier
                .safeDrawingPadding(),
            sheetState = sheetState,
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                }
            },
            content = {
                when (state.sheetType) {
                    SheetType.RoomSheet -> Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 12.dp,
                                bottom = 24.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        BottomSheetItem(
                            icon = R.drawable.ic_edit,
                            text = "Ubah"
                        ) {
                            scope.launch {
                                sheetState.hide()
                            }
                            navHostController.navigate(NavRoute.EditRoom.route) {
                                launchSingleTop = true
                            }
                        }
                        BottomSheetItem(
                            icon = R.drawable.ic_trash,
                            text = "Hapus",
                            color = color.error
                        ) {
                            onAction(EachRoomAction.OnDeleteRoom)
                        }
                    }

                    SheetType.MemberSheet -> Column(
                        modifier = Modifier
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 12.dp,
                                bottom = 24.dp
                            ),
                    ) {
                        Text(
                            text = "Lakukan aksi untuk akun",
                            style = typography.titleSmall,
                            color = Color.Black
                        )
                        Text(
                            text = state.selectedMember?.email ?: "",
                            style = typography.bodySmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.padding(bottom = 24.dp))
                        BottomSheetItem(
                            icon = R.drawable.ic_trash,
                            text = "Hapus",
                            color = color.error
                        ) {
                            onAction(EachRoomAction.OnDeleteMember)
                        }
                    }

                    SheetType.AddMemberSheet -> Column(
                        modifier = Modifier
                            .padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 12.dp,
                            )
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Masukan Email Anggota Baru",
                            style = typography.titleSmall,
                            color = Color.Black
                        )
                        BaseBasicTextField(
                            modifier = Modifier
                                .height(18.dp),
                            label = "contoh: johndoe3@gmail.com",
                            value = state.emailEmitMemberValue,
                            onValueChange = {
                                onAction(EachRoomAction.OnEmailEmitMemberValueChange(it))
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            textStyle = typography.bodySmall,
                        )
                        Spacer(modifier = Modifier.padding(bottom = 24.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = "Tambahkan Anggota",
                                style = typography.labelLarge,
                                color = color.primary,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        onAction(EachRoomAction.OnEmitMember)
                                    }
                            )
                        }
                    }
                }

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
                            text = state.roomName,
                            style = typography.bodyMedium,
                            color = color.onPrimary
                        )
                        Text(
                            text = state.additional,
                            style = typography.labelLarge,
                            color = color.onPrimary
                        )
                    }
                    if (state.currentContent == 0)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dots_vertical),
                            contentDescription = "",
                            tint = color.onPrimary,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(24.dp)
                                .clickable {
                                    scope.launch {
                                        sheetState.show()
                                    }
                                    onAction(EachRoomAction.OnShowSheet(SheetType.RoomSheet))
                                }
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
                        scope.launch {
                            sheetState.show()
                        }
                        onAction(EachRoomAction.OnShowSheet(SheetType.AddMemberSheet))
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
                onAction = onAction,
                onShowMemberSheet = {
                    scope.launch {
                        sheetState.show()
                    }
                    onAction(EachRoomAction.OnShowSheet(SheetType.MemberSheet))
                }
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