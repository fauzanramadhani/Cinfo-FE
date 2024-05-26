package com.ndc.cinfoadmin.ui.feature.createroom

sealed interface CreateRoomEffect {
    data object None : CreateRoomEffect
    data object OnCreateRoomSuccess : CreateRoomEffect
}