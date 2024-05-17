package com.ndc.cinfoadmin.ui.feature.createpost

sealed interface CreatePostEffect {
    data object None: CreatePostEffect
    data object OnCreatePostSuccess: CreatePostEffect
}