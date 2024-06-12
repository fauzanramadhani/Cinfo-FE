package com.ndc.cinfo.ui.screen.post


data class PostState(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val createdAt: Long = 0L,
    val loading: Boolean = false,
    val error: String? = null,
)