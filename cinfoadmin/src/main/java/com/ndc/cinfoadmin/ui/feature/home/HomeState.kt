package com.ndc.cinfoadmin.ui.feature.home

import com.ndc.core.R
import com.ndc.core.data.datasource.remote.response.PostGlobalResponse

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
    // Main Screen
    val loadingPostGlobal: Boolean = false,
    val postGlobalMap: Map<String, PostGlobalResponse> = mapOf(),
    val errorPostGlobalMessage: Throwable? = null,
    val updateServerDialogShow: Boolean = false,
    val updateServerTvValue: String = ""
)
