package com.ndc.cinfoadmin.ui.feature.home

import com.ndc.core.R
import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.data.datasource.remote.response.RoomResponse

data class HomeState(
    // Root
    val bottomNavigationItems: List<BottomNavigationItem> = listOf(
        BottomNavigationItem(
            label = "Utama",
            unselectedIcon = R.drawable.ic_main,
            selectedIcon = R.drawable.ic_main_fill,
        ),
        BottomNavigationItem(
            label = "Kelas Saya",
            unselectedIcon = R.drawable.ic_room,
            selectedIcon = R.drawable.ic_room_fill,
        )
    ),
    val updateServerDialogShow: Boolean = false,
    val updateServerTvValue: String = "",
    // Main Screen
    val loadingPostGlobal: Boolean = false,
    val postGlobalMap: Map<String, PostGlobalResponse> = mapOf(),
    val errorLoadPostGlobal: Throwable? = null,

    // Room Screen
    val loadingRoom: Boolean = false,
    val roomMap: Map<String, RoomResponse> = mapOf(),
    val errorLoadRoom: Throwable? = null,
)
