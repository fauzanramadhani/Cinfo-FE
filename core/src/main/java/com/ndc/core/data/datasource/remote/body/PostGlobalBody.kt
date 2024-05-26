package com.ndc.core.data.datasource.remote.body

import com.google.gson.annotations.SerializedName

data class PostGlobalBody(
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("description")
    val description: String,
)
