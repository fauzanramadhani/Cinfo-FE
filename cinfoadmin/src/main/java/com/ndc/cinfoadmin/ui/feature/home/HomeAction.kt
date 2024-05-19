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

    // Main Screen
    data object OnObservePostGlobal : HomeAction
    data class OnItemPostGlobalClicked(
        val post: PostGlobalResponse
    ) : HomeAction

    // Room Screen
    data object OnObserveRoom : HomeAction
    data class OnItemRoomClicked(
        val room: RoomResponse
    )
}