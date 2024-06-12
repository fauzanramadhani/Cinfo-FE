package com.ndc.cinfo.ui.screen.post

sealed interface PostEffect {
    data object None: PostEffect
    data object OnNavigateUp: PostEffect
}