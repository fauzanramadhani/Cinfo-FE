package com.ndc.core.data.repository

import com.ndc.core.data.constant.PostEvent
import com.ndc.core.data.constant.PostOptions
import com.ndc.core.data.post.datasource.remote.response.PostGlobalResponse
import com.ndc.core.utils.SocketHandler
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val mSocketHandler: SocketHandler,
) {

    init {
        mSocketHandler.establishConnection()
    }

    fun obServePostGlobal(
        onSuccess: (data: PostGlobalResponse) -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        with(mSocketHandler) {
            saveObserve<PostGlobalResponse>(
                event = PostEvent.POST_GLOBAL,
                onSuccess = { response ->
                    getOptions().auth[PostOptions.POST_GLOBAL_OFFSET] =
                        response.clientOffset.toString()
                    onSuccess(response)
                },
                onFailure = onFailure
            )
        }
    }
}