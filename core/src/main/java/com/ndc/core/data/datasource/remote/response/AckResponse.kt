package com.ndc.core.data.datasource.remote.response

data class AckResponse(
    val status: String,
    val message: String? = null,
)
