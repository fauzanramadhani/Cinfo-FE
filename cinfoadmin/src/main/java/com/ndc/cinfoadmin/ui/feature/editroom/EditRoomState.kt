package com.ndc.cinfoadmin.ui.feature.editroom

data class EditRoomState(
    val roomNameValue: String = "",
    val currentRoomName: String = "",
    val additionalValue: String = "",
    val currentAdditional: String = "",
    val createRoomLoading: Boolean = false,
)
