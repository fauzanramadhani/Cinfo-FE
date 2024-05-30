package com.ndc.cinfoadmin.ui.feature.home

import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.data.datasource.remote.response.RoomResponse

sealed interface HomeAction {
    data class OnUpdateServerDialogShowChange(
        val show: Boolean
    ) : HomeAction

    data class OnUpdateServerTvChange(
        val value: String
    ) : HomeAction
    data object OnUpdateServer : HomeAction
    data object OnSavePostTypeGlobal : HomeAction
    data object OnSavePostTypePrivate : HomeAction

    // Main Screen
    data class OnItemPostGlobalClicked(
        val post: PostGlobalResponse
    ) : HomeAction

    // Room Screen
    data class OnItemRoomClicked(
        val room: RoomResponse
    ) : HomeAction
}