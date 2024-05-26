package com.ndc.cinfoadmin.ui.feature.eachroom

sealed interface EachRoomAction {
    data class OnScreenChange (
        val screen: Int
    ) : EachRoomAction
}