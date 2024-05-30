package com.ndc.cinfoadmin.ui.feature.eachroom

import com.ndc.core.R
import com.ndc.core.data.datasource.remote.response.MemberResponse
import com.ndc.core.data.datasource.remote.response.PostPrivateResponse
import com.ndc.core.ui.component.bar.BottomNavigationItem

data class EachRoomState(
    // Root
    val currentContent: Int = 0,
    val roomId: String = "",
    val roomName: String = "",
    val additional: String = "",
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
    ),
    val selectedMember: MemberResponse? = null,
    val sheetType: SheetType = SheetType.RoomSheet,
    val loadingObserve: Boolean = false,
    val loadingEmit: Boolean = false,
    val emailEmitMemberValue: String = "",
    // Post
    val postPrivate: Map<String, PostPrivateResponse>? = null,
    // Member
    val members: Map<String, MemberResponse>? = null,
)

enum class SheetType {
    RoomSheet,
    MemberSheet,
    AddMemberSheet
}
