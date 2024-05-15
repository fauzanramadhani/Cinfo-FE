package com.ndc.cinfoadmin.ui.feature.home

sealed interface HomeEffect {
    data object None: HomeEffect
    data object OnItemClicked: HomeEffect
}