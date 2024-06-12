package com.ndc.cinfo.ui.screen.post

import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.domain.GetPostGlobalCacheUseCase
import com.ndc.core.data.domain.GetPostPrivateCacheUseCase
import com.ndc.core.data.domain.GetPostTypeCacheUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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