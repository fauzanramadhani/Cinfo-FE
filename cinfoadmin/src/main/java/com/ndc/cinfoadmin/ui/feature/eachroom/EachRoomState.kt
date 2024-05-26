package com.ndc.cinfoadmin.ui.feature.eachroom

import com.ndc.core.R
import com.ndc.core.data.datasource.remote.response.RoomResponse
import com.ndc.core.ui.component.bar.BottomNavigationItem

data class EachRoomState(
    // Root
    val currentContent: Int = 0,
    val room: RoomResponse? = null,
    val bottomNavigationItems: List<BottomNavigationItem> = listOf(
        BottomNavigationItem(
            label = "Ruangan",
            unselectedIcon = R.drawable.ic_main,
            selectedIcon = R.drawable.ic_main_fill,
        ),
        BottomNavigationItem(
            label = "Anggota",
            unselectedIcon = R.drawable.ic_group,
            selectedIcon = R.drawable.ic_group_fill,
        )
    )
    // Post
    // Member
)
