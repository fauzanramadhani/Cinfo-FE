package com.ndc.cinfoadmin.ui.feature.createroom

data class CreateRoomState(
    val roomName: String = "",
    val additional: String = "",
    val createRoomLoading: Boolean = false,
    val createRoomError: String? = null,
)