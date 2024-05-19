package com.ndc.cinfoadmin.ui.feature.post

import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.domain.GetPostCacheUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostCacheUseCase: GetPostCacheUseCase,
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
        val postCache = getPostCacheUseCase.invoke()

        updateState {
            copy(
                id = postCache.id,
                title = postCache.title,
                description = postCache.description,
                createdAt = postCache.createdAt
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