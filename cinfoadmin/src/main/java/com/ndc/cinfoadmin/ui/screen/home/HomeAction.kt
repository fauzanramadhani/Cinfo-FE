package com.ndc.cinfoadmin.ui.screen.home

sealed interface HomeAction {
    data class OnUpdateServerDialogShowChange(
        val show: Boolean
    ) : HomeAction

    data class OnUpdateServerTvChange(
        val value: String
    ) : HomeAction

    data object OnUpdateServer : HomeAction

    // Main Screen
    data object OnObservePostGlobal : HomeAction
}