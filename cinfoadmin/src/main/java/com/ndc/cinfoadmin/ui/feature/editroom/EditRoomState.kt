package com.ndc.cinfoadmin.ui.feature.editroom

data class EditRoomState(
    val roomId: String = "",
    val roomNameValue: String = "",
    val currentRoomName: String = "",
    val additionalValue: String = "",
    val currentAdditional: String = "",
    val loading: Boolean = false,
    val error: Throwable? = null,
)
