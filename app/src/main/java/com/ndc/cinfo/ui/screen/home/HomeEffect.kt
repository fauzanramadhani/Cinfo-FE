package com.ndc.cinfo.ui.screen.home

sealed interface HomeEffect {
    data object None: HomeEffect
    data object OnNavigateToDetailPostScreen : HomeEffect
    data class OnShowToast(
        val message: String
    ) : HomeEffect
}