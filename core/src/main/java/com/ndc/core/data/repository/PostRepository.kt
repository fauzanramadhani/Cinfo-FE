package com.ndc.core.data.repository

import com.google.gson.Gson
import com.ndc.core.data.constant.Event
import com.ndc.core.data.constant.Options
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.datasource.remote.body.PostGlobalBody
import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.utils.SharedPreferencesManager
import com.ndc.core.utils.SocketHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val mSocketHandler: SocketHandler,
    private val sharedPreferencesManager: SharedPreferencesManager,
) {

    init {
        mSocketHandler.establishConnection()
    }

    fun observePostGlobal(): Flow<PostGlobalResponse> =
        mSocketHandler.observe(Event.POST_GLOBAL)

    fun updatePostGlobalOffset(offset: String) {
        mSocketHandler.mOptions?.auth?.set(Options.POST_GLOBAL_OFFSET, offset)
    }

    fun savePostCache(post: PostGlobalResponse) {
        sharedPreferencesManager.apply {
            saveString(SharedPref.POST_ID, post.id)
            saveString(SharedPref.POST_TITLE, post.title)
            saveString(SharedPref.POST_DESCRIPTION, post.description)
            saveLong(SharedPref.POST_CREATED_AT, post.createdAt)
            saveInt(SharedPref.POST_CLIENT_OFFSET, post.clientOffset)
        }
    }

    fun getPostCache(): PostGlobalResponse {
        val getId = sharedPreferencesManager.getString(SharedPref.POST_ID)
        val getTitle = sharedPreferencesManager.getString(SharedPref.POST_TITLE)
        val getDescription = sharedPreferencesManager.getString(SharedPref.POST_DESCRIPTION)
        val getCreatedAt = sharedPreferencesManager.getLong(SharedPref.POST_CREATED_AT)
        val getClientOffset = sharedPreferencesManager.getInt(SharedPref.POST_CLIENT_OFFSET)
        return PostGlobalResponse(
            id = getId,
            title = getTitle,
            description = getDescription,
            createdAt = getCreatedAt,
            clientOffset = getClientOffset
        )
    }

    fun emitPostGlobal(
        title: String,
        description: String
    ): Flow<Unit> {
        val body = Gson().toJson(PostGlobalBody(title, description))
        return mSocketHandler.emit(
            Event.CREATE_POST_GLOBAL,
            body
        )
    }
}