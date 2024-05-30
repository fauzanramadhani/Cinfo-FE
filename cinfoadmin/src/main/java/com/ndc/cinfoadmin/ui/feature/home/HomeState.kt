package com.ndc.cinfoadmin.ui.feature.home

import com.ndc.core.R
import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.data.datasource.remote.response.RoomResponse
import com.ndc.core.ui.component.bar.BottomNavigationItem

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
    val loading: Boolean = false,
    val error: Throwable? = null,

    // Main Screen
    val postGlobalResponseMap: Map<String, PostGlobalResponse>? = null,

    // Room Screen
    val roomMap: Map<String, RoomResponse>? = null,
)
