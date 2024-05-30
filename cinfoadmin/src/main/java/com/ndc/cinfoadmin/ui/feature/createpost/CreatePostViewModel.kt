package com.ndc.cinfoadmin.ui.feature.createpost

import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.domain.EmitPostGlobalUseCase
import com.ndc.core.data.domain.EmitPostPrivateUseCase
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
class CreatePostViewModel @Inject constructor(
    private val emitPostGlobalUseCase: EmitPostGlobalUseCase,
    private val emitPostPrivateUseCase: EmitPostPrivateUseCase,
    getPostTypeCacheUseCase: GetPostTypeCacheUseCase,
    private val getRoomCacheUseCase: GetRoomCacheUseCase,
) :
    BaseViewModel<CreatePostState, CreatePostAction, CreatePostEffect>(CreatePostState()) {

    private val postType = getPostTypeCacheUseCase.invoke()

    init {
        if (postType == SharedPref.POST_TYPE_PRIVATE) setRoomData()
    }

    override fun onAction(action: CreatePostAction) {
        when (action) {
            is CreatePostAction.OnDescriptionValueChange -> updateState { copy(descriptionValue = action.value) }
            CreatePostAction.CreatePost -> if (postType == SharedPref.POST_TYPE_PRIVATE)
                emitPostPrivate()
            else emitPostGlobal()

            is CreatePostAction.OnTitleValueChange -> updateState { copy(titleValue = action.value) }
        }
    }

    private fun setRoomData() {
        updateState { copy(room = getRoomCacheUseCase.invoke()) }
    }

    @OptIn(FlowPreview::class)
    private fun emitPostPrivate() = viewModelScope.launch {
        state.value.room?.let { room ->
            emitPostPrivateUseCase.invoke(
                room.id,
                state.value.titleValue,
                state.value.descriptionValue,
            ).take(1)
                .onStart {
                    updateState { copy(createPostLoading = true) }
                }
                .onEach {
                    sendEffect(CreatePostEffect.OnCreatePostSuccess)
                }
                .timeout(3000.milliseconds)
                .catch {
                    updateState { copy(createPostError = it.message) }
                }
                .onCompletion {
                    updateState { copy(createPostLoading = false) }
                    delay(300)
                    updateState { copy(createPostError = null) }
                }
                .collect()
        }
    }

    @OptIn(FlowPreview::class)
    private fun emitPostGlobal() = viewModelScope.launch {
        emitPostGlobalUseCase.invoke(
            state.value.titleValue,
            state.value.descriptionValue,
        )
            .take(1)
            .onStart {
                updateState {
                    copy(createPostLoading = true)
                }
            }
            .onEach {
                sendEffect(CreatePostEffect.OnCreatePostSuccess)
            }
            .timeout(3000.milliseconds)
            .catch {
                updateState { copy(createPostError = it.message) }
            }
            .onCompletion {
                updateState { copy(createPostLoading = false) }
                delay(300)
                updateState { copy(createPostError = null) }
            }
            .collect()
    }
}