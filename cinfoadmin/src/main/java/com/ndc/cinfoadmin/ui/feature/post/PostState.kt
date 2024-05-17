package com.ndc.cinfoadmin.ui.feature.post

data class PostState(
    val currentScreen: Int = 0,
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val createdAt: Long = 0L,
    // Edit Post Screen
    val descriptionValue: String = "",
    val titleValue: String = "",
)