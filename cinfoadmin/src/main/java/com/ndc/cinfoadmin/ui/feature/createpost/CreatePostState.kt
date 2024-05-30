package com.ndc.cinfoadmin.ui.feature.createpost

import com.ndc.core.data.datasource.remote.response.RoomResponse

data class CreatePostState(
    val room: RoomResponse? = null,
    val titleValue: String = "",
    val descriptionValue: String = "",
    val createPostLoading: Boolean = false,
    val createPostError: String? = null,
)