package com.ndc.cinfoadmin.ui.feature.eachroom

sealed interface EachRoomEffect {
    data object None : EachRoomEffect
    data object OnNavigateToPost : EachRoomEffect
    data object OnDeleteRoomSuccess: EachRoomEffect
    data object OnEmitMemberSuccess: EachRoomEffect
    data object OnDeleteMemberSuccess: EachRoomEffect
    data class OnShowToast(
        val message: String
    ) : EachRoomEffect
}