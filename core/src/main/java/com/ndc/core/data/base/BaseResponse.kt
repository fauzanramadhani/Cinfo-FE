package com.ndc.core.data.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<out T>(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("status")
    val data: T? = null
)
