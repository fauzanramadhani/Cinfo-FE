package com.ndc.core.data.repository

import com.google.gson.Gson
import com.ndc.core.data.constant.PostEvent
import com.ndc.core.data.constant.PostOptions
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.datasource.remote.body.PostGlobalBody
import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.utils.SharedPreferencesManager
import com.ndc.core.utils.SocketHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val mSocketHandler: SocketHandler,
    private val sharedPreferencesManager: SharedPreferencesManager
) {

    init {
        mSocketHandler.establishConnection()
    }

    fun observePostGlobal(): Flow<PostGlobalResponse> =
        mSocketHandler.observe(PostEvent.POST_GLOBAL)

    fun updatePostGlobalOffset(offset: String) {
        mSocketHandler.mOptions?.auth?.set(PostOptions.POST_GLOBAL_OFFSET, offset)
    }

    fun emitUpdatePostGlobal(
        title: String,
        description: String
    ): Flow<Unit> {
        val body = Gson().toJson(PostGlobalBody(title, description))
        return mSocketHandler.emit(
            PostEvent.CREATE_POST_GLOBAL,
            body
        )
    }

    fun updateServerAddress(address: String) =
        sharedPreferencesManager.saveString(SharedPref.SERVER_ADDRESS, address)
}