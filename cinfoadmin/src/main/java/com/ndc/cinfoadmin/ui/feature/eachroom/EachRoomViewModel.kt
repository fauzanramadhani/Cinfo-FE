package com.ndc.cinfoadmin.ui.feature.eachroom

import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.domain.GetRoomCacheUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EachRoomViewModel @Inject constructor(
    private val getRoomCacheUseCase: GetRoomCacheUseCase,
) : BaseViewModel<EachRoomState, EachRoomAction, EachRoomEffect>(EachRoomState()) {

    init {
        updateState { copy(room = getRoomCacheUseCase.invoke()) }
    }

    override fun onAction(action: EachRoomAction) {
        when (action) {
            is EachRoomAction.OnScreenChange -> updateState { copy(currentContent = action.screen) }
        }
    }
}