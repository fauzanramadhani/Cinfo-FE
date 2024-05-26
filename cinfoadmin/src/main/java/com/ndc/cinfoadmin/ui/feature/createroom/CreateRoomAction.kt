package com.ndc.cinfoadmin.ui.feature.createroom

sealed interface CreateRoomAction {
    data object CreateRoom: CreateRoomAction
    data class OnRoomNameValueChange(
        val value: String
    ): CreateRoomAction
    data class OnAdditionalValueChange(
        val value: String
    ) : CreateRoomAction
}