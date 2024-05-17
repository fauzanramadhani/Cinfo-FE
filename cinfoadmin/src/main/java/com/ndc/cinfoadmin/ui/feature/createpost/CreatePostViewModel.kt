package com.ndc.cinfoadmin.ui.feature.createpost

import com.ndc.core.data.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor() :
    BaseViewModel<CreatePostState, CreatePostAction, CreatePostEffect>(CreatePostState()) {
    override fun onAction(action: CreatePostAction) {
        when (action) {
            is CreatePostAction.OnDescriptionValueChange -> updateState { copy(descriptionValue = action.value) }
            CreatePostAction.OnDone -> {}
            is CreatePostAction.OnTitleValueChange -> updateState { copy(titleValue = action.value) }
        }
    }

}