package com.ndc.cinfoadmin.ui.feature.post

import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.utils.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) : BaseViewModel<PostState, PostAction, PostEffect>(
    PostState()
) {
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
            PostAction.OnEditPost -> {

            }
        }
    }

    private fun setData() {
        val getId = sharedPreferencesManager.getString(SharedPref.POST_ID)
        val getTitle = sharedPreferencesManager.getString(SharedPref.POST_TITLE)
        val getDescription = sharedPreferencesManager.getString(SharedPref.POST_DESCRIPTION)
        val getCreatedAt = sharedPreferencesManager.getLong(SharedPref.POST_CREATED_AT)

        updateState {
            copy(
                id = getId,
                title = getTitle,
                description = getDescription,
                createdAt = getCreatedAt
            )
        }
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