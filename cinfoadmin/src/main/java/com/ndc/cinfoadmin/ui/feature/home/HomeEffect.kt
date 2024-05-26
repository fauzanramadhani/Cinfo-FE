package com.ndc.cinfoadmin.ui.feature.home

sealed interface HomeEffect {
    data object None : HomeEffect
    data object OnItemPostClicked : HomeEffect
    data object OnItemRoomClicked : HomeEffect
}