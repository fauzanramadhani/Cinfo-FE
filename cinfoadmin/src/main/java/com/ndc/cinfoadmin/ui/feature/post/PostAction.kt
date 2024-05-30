package com.ndc.cinfoadmin.ui.feature.post

sealed interface PostAction {
    data class OnChangeScreen(
        val screen: Int
    ) : PostAction
    data object OnBackPressed : PostAction

    // Detail Post
    data object OnDeletePost: PostAction
    // Edit Post Screen
    data object OnEditPost: PostAction
    data class OnTitleValueChange(
        val value: String = ""
    ) : PostAction
    data class OnDescriptionValueChange(
        val value: String = ""
    ) : PostAction
}