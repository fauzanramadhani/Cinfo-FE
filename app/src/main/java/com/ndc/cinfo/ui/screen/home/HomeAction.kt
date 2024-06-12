package com.ndc.cinfo.ui.screen.home

import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.data.datasource.remote.response.PostPrivateResponse

sealed interface HomeAction {
    data class OnContentChange(
        val content: Int
    ) : HomeAction

    data class OnUpdateServerDialogShowChange(
        val show: Boolean
    ) : HomeAction

    // Main Screen
    data class OnUpdateServerTvChange(
        val value: String
    ) : HomeAction

    data object OnUpdateServer : HomeAction
    data class OnItemPostGlobalClicked(
        val post: PostGlobalResponse
    ) : HomeAction

    // Room Screen
    data class OnObservePostPrivate(
        val roomId: String
    ) : HomeAction
    data class OnItemPostPrivateClicked(
        val post: PostPrivateResponse
    ) : HomeAction

    // Account Screen
    data object OnLogout : HomeAction
}