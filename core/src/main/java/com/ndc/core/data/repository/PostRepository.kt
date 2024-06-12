package com.ndc.core.data.repository

import com.google.gson.Gson
import com.ndc.core.data.constant.Event
import com.ndc.core.data.constant.Options
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.datasource.remote.body.CreatePostGlobalBody
import com.ndc.core.data.datasource.remote.body.CreatePostPrivateBody
import com.ndc.core.data.datasource.remote.body.EditPostGlobalBody
import com.ndc.core.data.datasource.remote.body.EditPrivatePostBody
import com.ndc.core.data.datasource.remote.response.PostGlobalResponse
import com.ndc.core.data.datasource.remote.response.PostPrivateResponse
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

    fun getPostType() = sharedPreferencesManager.getString(SharedPref.POST_TYPE)

    // Post Global

    fun observePostGlobal(): Flow<PostGlobalResponse> =
        mSocketHandler.observe(Event.POST_GLOBAL)

    fun observeDeletePostGlobal(): Flow<String> =
        mSocketHandler.observe(Event.ON_DELETE_POST_GLOBAL)

    fun updatePostGlobalOffset(offset: String) {
        mSocketHandler.mOptions?.auth?.set(Options.POST_GLOBAL_OFFSET, offset)
    }

    fun savePostTypeGlobal() {
        sharedPreferencesManager.saveString(SharedPref.POST_TYPE, SharedPref.POST_TYPE_GLOBAL)
    }

    fun savePostGlobalCache(post: PostGlobalResponse) {
        sharedPreferencesManager.apply {
            saveString(SharedPref.POST_ID, post.id)
            saveString(SharedPref.POST_TITLE, post.title)
            saveString(SharedPref.POST_DESCRIPTION, post.description)
            saveLong(SharedPref.POST_CREATED_AT, post.createdAt)
            saveInt(SharedPref.POST_CLIENT_OFFSET, post.clientOffset)
        }
    }

    fun getPostGlobalCache(): PostGlobalResponse {
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
        val body = Gson().toJson(CreatePostGlobalBody(title, description))
        return mSocketHandler.emit(
            Event.CREATE_POST_GLOBAL,
            body
        )
    }

    fun emitEditPostGlobal(
        postId: String,
        title: String,
        description: String
    ): Flow<Unit> {
        val body = Gson().toJson(EditPostGlobalBody(postId, title, description))
        return mSocketHandler.emit(
            Event.EDIT_POST_GLOBAL,
            body
        )
    }

    fun emitDeletePostGlobal(id: String): Flow<Unit> {
        val body = Gson().toJson(mapOf("post_id" to id))
        return mSocketHandler.emit(
            Event.DELETE_POST_GLOBAL,
            body
        )
    }

    // Post Private

    fun observePostPrivate(roomId: String): Flow<PostPrivateResponse> =
        mSocketHandler.observe(roomId + Event.POST_PRIVATE)

    fun observeDeletePostPrivate(roomId: String): Flow<String> =
        mSocketHandler.observe(roomId + Event.ON_DELETE_POST_PRIVATE)

    fun updatePostPrivateOffset(offset: String) =
        mSocketHandler.mOptions?.auth?.set(Options.POST_PRIVATE_OFFSET, offset)

    fun emitCreatePostPrivate(
        roomId: String,
        title: String,
        description: String
    ): Flow<Unit> {
        val body = Gson().toJson(CreatePostPrivateBody(roomId, title, description))
        return mSocketHandler.emit(Event.PRIVATE_POST_PRIVATE, body)
    }

    fun emitEditPostPrivate(
        postId: String,
        title: String,
        description: String
    ): Flow<Unit> {
        val body = Gson().toJson(EditPrivatePostBody(postId, description, title))
        return mSocketHandler.emit(Event.EDIT_POST_PRIVATE, body)
    }

    fun emitDeletePostPrivate(id: String): Flow<Unit> {
        val body = Gson().toJson(mapOf("post_id" to id))
        return mSocketHandler.emit(
            Event.DELETE_POST_PRIVATE,
            body
        )
    }

    fun savePostTypePrivate() {
        sharedPreferencesManager.saveString(SharedPref.POST_TYPE, SharedPref.POST_TYPE_PRIVATE)
    }

    fun savePostPrivateCache(post: PostPrivateResponse) {
        sharedPreferencesManager.apply {
            saveString(SharedPref.POST_ID, post.id)
            saveString(SharedPref.POST_TITLE, post.title)
            saveString(SharedPref.POST_DESCRIPTION, post.description)
            saveLong(SharedPref.POST_CREATED_AT, post.createdAt)
            saveString(SharedPref.POST_ROOM_ID, post.roomId)
            saveInt(SharedPref.POST_CLIENT_OFFSET, post.clientOffset)
        }
    }

    fun getPostPrivateCache(): PostPrivateResponse {
        val getId = sharedPreferencesManager.getString(SharedPref.POST_ID)
        val getTitle = sharedPreferencesManager.getString(SharedPref.POST_TITLE)
        val getDescription = sharedPreferencesManager.getString(SharedPref.POST_DESCRIPTION)
        val getCreatedAt = sharedPreferencesManager.getLong(SharedPref.POST_CREATED_AT)
        val getPostRoomId = sharedPreferencesManager.getString(SharedPref.POST_ROOM_ID)
        val getClientOffset = sharedPreferencesManager.getInt(SharedPref.POST_CLIENT_OFFSET)
        return PostPrivateResponse(
            id = getId,
            title = getTitle,
            description = getDescription,
            createdAt = getCreatedAt,
            roomId = getPostRoomId,
            clientOffset = getClientOffset
        )
    }
}
