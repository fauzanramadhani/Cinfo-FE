package com.ndc.cinfo.ui.screen.home

import com.google.firebase.auth.FirebaseUser
import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.data.datasource.remote.response.PostPrivateResponse
import com.ndc.core.data.datasource.remote.response.RoomResponse

data class HomeState(
    val content: Int = 0,
    val firebaseUser: FirebaseUser? = null,
    val loading: Boolean = false,
    val error: Throwable? = null,
    val room: RoomResponse? = null,
    // Main
    val postGlobalMap: Map<String, PostGlobalResponse>? = null,
    val updateServerDialogShow: Boolean = false,
    val updateServerTvValue: String = "",
    // Room
    val postPrivateMap: Map<String, PostPrivateResponse>? = null,
)