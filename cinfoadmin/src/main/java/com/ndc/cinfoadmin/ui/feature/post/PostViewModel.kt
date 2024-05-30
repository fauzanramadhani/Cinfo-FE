package com.ndc.cinfoadmin.ui.feature.post

import androidx.lifecycle.viewModelScope
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
    private val getRoomCacheUseCase: GetRoomCacheUseCase,
    private val emitDeletePostGlobalUseCase: EmitDeletePostGlobalUseCase,
    private val emitEditPostGlobalUseCase: EmitEditPostGlobalUseCase,
    private val emitEditPostPrivateUseCase: EmitEditPostPrivateUseCase,
    private val emitDeletePostPrivateUseCase: EmitDeletePostPrivateUseCase
) : BaseViewModel<PostState, PostAction, PostEffect>(
    PostState()
) {
    private val postType = getPostTypeCacheUseCase.invoke()

    init {
        setData()
    }

    override fun onAction(action: PostAction) {
        when (action) {
            is PostAction.OnChangeScreen -> {
                if (action.screen == 1) {
                    updateState {
                        copy(
                            titleValue = title,
                            descriptionValue = description
                        )
                    }
                }
                updateState {
                    copy(currentScreen = action.screen)
                }
            }

            PostAction.OnBackPressed -> onBackPressed()
            is PostAction.OnTitleValueChange -> updateState { copy(titleValue = action.value) }
            is PostAction.OnDescriptionValueChange -> updateState { copy(descriptionValue = action.value) }
            PostAction.OnEditPost -> if (postType == SharedPref.POST_TYPE_GLOBAL) emitEditGlobalPost() else emitEditPrivatePost()
            PostAction.OnDeletePost -> if (postType == SharedPref.POST_TYPE_GLOBAL) emitDeleteGlobalPost() else emitDeletePrivatePost()
        }
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
                val getRoomCacheUseCase = getRoomCacheUseCase.invoke()
                updateState {
                    copy(
                        id = postPrivateCache.id,
                        title = postPrivateCache.title,
                        description = postPrivateCache.description,
                        createdAt = postPrivateCache.createdAt,
                        room = getRoomCacheUseCase,
                    )
                }
            }

        }

    }

    @OptIn(FlowPreview::class)
    private fun emitEditGlobalPost() = viewModelScope.launch {
        val state = state.value
        emitEditPostGlobalUseCase
            .invoke(state.id, state.titleValue, state.descriptionValue)
            .take(1)
            .onStart {
                updateState { copy(loading = true) }
            }
            .onEach {
                updateState {
                    copy(
                        title = state.titleValue,
                        description = state.descriptionValue,
                        titleValue = "",
                        descriptionValue = "",
                        currentScreen = 0
                    )
                }
            }
            .timeout(3000.milliseconds)
            .catch {
                updateState { copy(error = it.message) }
            }
            .onCompletion {
                updateState { copy(loading = false) }
                delay(300)
                updateState { copy(error = null) }
            }
            .collect()
    }

    @OptIn(FlowPreview::class)
    private fun emitDeleteGlobalPost() = viewModelScope.launch {
        emitDeletePostGlobalUseCase.invoke(state.value.id)
            .take(1)
            .onStart {
                updateState { copy(loading = true) }
            }
            .onEach {
                sendEffect(PostEffect.OnNavigateUp)
            }
            .timeout(3000.milliseconds)
            .catch {
                updateState { copy(error = it.message) }
            }
            .onCompletion {
                updateState { copy(loading = false) }
                delay(300)
                updateState { copy(error = null) }
            }
            .collect()
    }

    @OptIn(FlowPreview::class)
    private fun emitDeletePrivatePost() = viewModelScope.launch {
        emitDeletePostPrivateUseCase.invoke(state.value.id)
            .take(1)
            .onStart {
                updateState { copy(loading = true) }
            }
            .onEach {
                sendEffect(PostEffect.OnNavigateUp)
            }
            .timeout(3000.milliseconds)
            .catch {
                updateState { copy(error = it.message) }
            }
            .onCompletion {
                updateState { copy(loading = false) }
                delay(300)
                updateState { copy(error = null) }
            }
            .collect()
    }

    @OptIn(FlowPreview::class)
    private fun emitEditPrivatePost() = viewModelScope.launch {
        val state = state.value
        emitEditPostPrivateUseCase
            .invoke(state.id, state.titleValue, state.descriptionValue)
            .take(1)
            .onStart {
                updateState { copy(loading = true) }
            }
            .onEach {
                updateState {
                    copy(
                        title = state.titleValue,
                        description = state.descriptionValue,
                        titleValue = "",
                        descriptionValue = "",
                        currentScreen = 0
                    )
                }
            }
            .timeout(3000.milliseconds)
            .catch {
                updateState { copy(error = it.message) }
            }
            .onCompletion {
                updateState { copy(loading = false) }
                delay(300)
                updateState { copy(error = null) }
            }
            .collect()
    }

    private fun onBackPressed() {
        if (state.value.currentScreen > 0)
            updateState {
                copy(
                    currentScreen = 0
                )
            }
        else sendEffect(PostEffect.OnNavigateUp)
    }

}