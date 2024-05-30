package com.ndc.core.data.datasource.remote.body

import com.google.gson.annotations.SerializedName

data class CreatePostPrivateBody(
    @field:SerializedName("room_id")
    val roomId: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("description")
    val description: String,
)
