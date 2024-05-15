package com.ndc.cinfoadmin.ui.feature.post

sealed interface PostEffect {
    data object None: PostEffect
    data object OnNavigateUp: PostEffect
}