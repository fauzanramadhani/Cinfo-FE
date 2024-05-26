package com.ndc.cinfoadmin.ui.feature.createpost

import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.domain.EmitPostGlobalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val emitPostGlobalUseCase: EmitPostGlobalUseCase,
) :
    BaseViewModel<CreatePostState, CreatePostAction, CreatePostEffect>(CreatePostState()) {
    override fun onAction(action: CreatePostAction) {
        when (action) {
            is CreatePostAction.OnDescriptionValueChange -> updateState { copy(descriptionValue = action.value) }
            CreatePostAction.CreatePost -> emitPostAction(
                state.value.titleValue,
                state.value.descriptionValue
            )

            is CreatePostAction.OnTitleValueChange -> updateState { copy(titleValue = action.value) }
        }
    }

    private fun emitPostAction(
        title: String,
        description: String
    ) = viewModelScope.launch {
        emitPostGlobalUseCase.invoke(title, description)
            .take(1)
            .onStart {
                updateState {
                    copy(createPostLoading = true)
                }
            }
            .onEach {
                sendEffect(CreatePostEffect.OnCreatePostSuccess)
            }
            .catch {
                updateState { copy(createPostError = it.message,) }
            }
            .onCompletion {
                updateState { copy(createPostLoading = false,) }
                delay(300)
                updateState { copy(createPostError = null,) }
            }
            .collect()
    }

}