package com.ndc.cinfoadmin.ui.feature.post

import com.ndc.core.data.datasource.remote.response.RoomResponse

data class PostState(
    val currentScreen: Int = 0,
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val createdAt: Long = 0L,
    val loading: Boolean = false,
    val room: RoomResponse? = null,
    // Detail Post
    val error: String? = null,
    // Edit Post Screen
    val descriptionValue: String = "",
    val titleValue: String = "",
)