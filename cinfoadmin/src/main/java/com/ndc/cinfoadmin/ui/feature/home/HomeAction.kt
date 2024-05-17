package com.ndc.cinfoadmin.ui.feature.home

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
    data class OnItemClicked(
        val id: String,
        val title: String,
        val description: String,
        val createdAt: Long
    ) : HomeAction
}