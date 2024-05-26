package com.ndc.cinfoadmin.ui.feature.eachroom

sealed interface EachRoomEffect {
    data object None : EachRoomEffect
    data object OnPostItemClicked : EachRoomEffect
}