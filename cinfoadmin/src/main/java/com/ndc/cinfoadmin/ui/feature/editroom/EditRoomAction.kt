package com.ndc.cinfoadmin.ui.feature.editroom

sealed interface EditRoomAction {
    data object EditRoom: EditRoomAction
    data class OnRoomNameValueChange(
        val value: String
    ): EditRoomAction
    data class OnAdditionalValueChange(
        val value: String
    ) : EditRoomAction
}