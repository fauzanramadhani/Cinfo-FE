package com.ndc.cinfoadmin.ui.feature.createpost

sealed interface CreatePostAction {
    data object OnDone: CreatePostAction
    data class OnTitleValueChange(
        val value: String,
    ): CreatePostAction
    data class OnDescriptionValueChange(
        val value: String,
    ): CreatePostAction
}