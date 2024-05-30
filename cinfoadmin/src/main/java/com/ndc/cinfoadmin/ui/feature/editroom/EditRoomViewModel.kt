package com.ndc.cinfoadmin.ui.feature.editroom

import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.domain.EmitEditRoomUseCase
import com.ndc.core.data.domain.GetRoomCacheUseCase
import com.ndc.core.data.domain.SaveRoomCacheUseCase
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
class EditRoomViewModel @Inject constructor(
    getRoomCacheUseCase: GetRoomCacheUseCase,
    private val emitEditRoomUseCase: EmitEditRoomUseCase,
    private val saveRoomCacheUseCase: SaveRoomCacheUseCase,
) : BaseViewModel<EditRoomState, EditRoomAction, EditRoomEffect>(
    EditRoomState()
) {
    private val room = getRoomCacheUseCase.invoke()

    init {
        updateState {
            copy(
                roomId = room.id,
                currentRoomName = room.roomName,
                currentAdditional = room.additional,
                roomNameValue = room.roomName,
                additionalValue = room.additional,
            )
        }
    }

    override fun onAction(action: EditRoomAction) {
        when (action) {
            EditRoomAction.EditRoom -> editRoom()
            is EditRoomAction.OnRoomNameValueChange -> updateState { copy(roomNameValue = action.value) }
            is EditRoomAction.OnAdditionalValueChange -> updateState { copy(additionalValue = action.value) }
        }
    }

    private fun editRoom() = viewModelScope.launch {
        emitEditRoomUseCase
            .invoke(
                roomId = state.value.roomId,
                roomName = state.value.roomNameValue,
                additional = state.value.additionalValue
            )
            .take(1)
            .onStart {
                updateState { copy(loading = true) }
            }
            .onEach {
                sendEffect(EditRoomEffect.OnEditRoomSuccess)
                saveRoomCacheUseCase.invoke(
                    room.copy(
                        id = state.value.roomId,
                        roomName = state.value.roomNameValue,
                        additional = state.value.additionalValue
                    )
                )
            }
            .catch {
                updateState { copy(error = it) }
            }
            .onCompletion {
                updateState { copy(loading = false) }
                delay(1000)
                updateState { copy(error = null) }
            }
            .collect()
    }
}