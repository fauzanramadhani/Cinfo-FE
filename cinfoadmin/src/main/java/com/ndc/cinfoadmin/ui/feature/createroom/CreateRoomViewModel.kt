package com.ndc.cinfoadmin.ui.feature.createroom

import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.domain.EmitRoomUseCase
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
class CreateRoomViewModel @Inject constructor(
    private val emitRoomUseCase: EmitRoomUseCase,
) : BaseViewModel<CreateRoomState, CreateRoomAction, CreateRoomEffect>(
    CreateRoomState()
) {
    override fun onAction(action: CreateRoomAction) {
        when (action) {
            CreateRoomAction.CreateRoom -> emitCreateRoom(state.value.roomName, state.value.additional)
            is CreateRoomAction.OnRoomNameValueChange -> updateState { copy(roomName = action.value) }
            is CreateRoomAction.OnAdditionalValueChange -> updateState { copy(additional = action.value) }
        }
    }

    private fun emitCreateRoom(
        roomName: String,
        additional: String,
    ) = viewModelScope.launch {
        emitRoomUseCase.invoke(roomName, additional)
            .take(1)
            .onStart {
                updateState { copy(createRoomLoading = true) }
            }
            .onEach {
                sendEffect(CreateRoomEffect.OnCreateRoomSuccess)
            }
            .catch {
                updateState { copy(createRoomError = it.message) }
            }
            .onCompletion {
                updateState { copy(createRoomLoading = false) }
                delay(300)
                updateState { copy(createRoomError = null) }
            }
            .collect()
    }
}