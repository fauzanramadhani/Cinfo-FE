package com.ndc.cinfo.ui.screen.post

import androidx.lifecycle.viewModelScope
import com.ndc.cinfo.ui.screen.post.PostAction
import com.ndc.cinfo.ui.screen.post.PostEffect
import com.ndc.cinfo.ui.screen.post.PostState
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.domain.EmitDeletePostGlobalUseCase
import com.ndc.core.data.domain.EmitDeletePostPrivateUseCase
import com.ndc.core.data.domain.EmitEditPostGlobalUseCase
import com.ndc.core.data.domain.EmitEditPostPrivateUseCase
import com.ndc.core.data.domain.GetPostGlobalCacheUseCase
import com.ndc.core.data.domain.GetPostPrivateCacheUseCase
import com.ndc.core.data.domain.GetPostTypeCacheUseCase
import com.ndc.core.data.domain.GetRoomCacheUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PostViewModel @Inject constructor(
    getPostTypeCacheUseCase: GetPostTypeCacheUseCase,
    private val getPostGlobalCacheUseCase: GetPostGlobalCacheUseCase,
    private val getPostPrivateCacheUseCase: GetPostPrivateCacheUseCase,
) : BaseViewModel<PostState, PostAction, PostEffect>(
    PostState()
) {
    private val postType = getPostTypeCacheUseCase.invoke()

    init {
        setData()
    }

    override fun onAction(action: PostAction) {

    }

    private fun setData() {
        when (postType) {
            SharedPref.POST_TYPE_GLOBAL -> {
                val postGlobalCache = getPostGlobalCacheUseCase.invoke()
                updateState {
                    copy(
                        id = postGlobalCache.id,
                        title = postGlobalCache.title,
                        description = postGlobalCache.description,
                        createdAt = postGlobalCache.createdAt,
                    )
                }
            }

            SharedPref.POST_TYPE_PRIVATE -> {
                val postPrivateCache = getPostPrivateCacheUseCase.invoke()
                updateState {
                    copy(
                        id = postPrivateCache.id,
                        title = postPrivateCache.title,
                        description = postPrivateCache.description,
                        createdAt = postPrivateCache.createdAt,
                    )
                }
            }

        }

    }

}