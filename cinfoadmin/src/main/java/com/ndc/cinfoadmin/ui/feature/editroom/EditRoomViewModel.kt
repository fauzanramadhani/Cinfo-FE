package com.ndc.cinfoadmin.ui.feature.editroom

import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditRoomViewModel @Inject constructor(

) : BaseViewModel<EditRoomState, EditRoomAction, EditRoomEffect>(
    EditRoomState()
) {
    override fun onAction(action: EditRoomAction) {
        when (action) {
            EditRoomAction.EditRoom -> editRoom()
            is EditRoomAction.OnRoomNameValueChange -> updateState { copy(roomNameValue = action.value) }
            is EditRoomAction.OnAdditionalValueChange -> updateState { copy(additionalValue = action.value) }
        }
    }

    private fun editRoom() = viewModelScope.launch {

    }
}