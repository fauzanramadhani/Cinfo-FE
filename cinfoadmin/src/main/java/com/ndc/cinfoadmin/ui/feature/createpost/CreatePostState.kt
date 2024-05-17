package com.ndc.cinfoadmin.ui.feature.createpost

data class CreatePostState(
    val targetRoom: String = "Universitas Cendekia Abditama",
    val titleValue: String = "",
    val descriptionValue: String = "",
    val createPostLoading: Boolean = false,
    val createPostError: String? = null,
)