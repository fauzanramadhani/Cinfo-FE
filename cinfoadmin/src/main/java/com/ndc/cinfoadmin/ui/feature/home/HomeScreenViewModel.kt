package com.ndc.cinfoadmin.ui.feature.home

import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.domain.ObservePostGlobalUseCase
import com.ndc.core.data.domain.ObserveRoomUseCase
import com.ndc.core.data.domain.SavePostCacheUseCase
import com.ndc.core.data.domain.UpdatePostGlobalOffsetUseCase
import com.ndc.core.data.domain.UpdateRoomOffsetUseCase
import com.ndc.core.data.domain.UpdateServerAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val observePostGlobalUseCase: ObservePostGlobalUseCase,
    private val updatePostGlobalOffsetUseCase: UpdatePostGlobalOffsetUseCase,
    private val updateServerAddressUseCase: UpdateServerAddressUseCase,
    private val savePostCacheUseCase: SavePostCacheUseCase,
    private val observeRoomUseCase: ObserveRoomUseCase,
    private val updateRoomOffsetUseCase: UpdateRoomOffsetUseCase
) : BaseViewModel<HomeState, HomeAction, HomeEffect>(
    HomeState()
) {

    init {
        onAction(HomeAction.OnObservePostGlobal)
    }

    override fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnObservePostGlobal -> observePostGlobal()
            is HomeAction.OnUpdateServerDialogShowChange -> updateState {
                copy(
                    updateServerDialogShow = action.show
                )
            }

            is HomeAction.OnUpdateServerTvChange -> updateState {
                copy(updateServerTvValue = action.value)
            }

            is HomeAction.OnUpdateServer -> updateServerAddressUseCase.invoke(state.value.updateServerTvValue)
            is HomeAction.OnItemPostGlobalClicked -> savePostCacheUseCase.invoke(action.post)
                .also {
                    sendEffect(HomeEffect.OnItemClicked)
                }

            HomeAction.OnObserveRoom -> TODO()
        }
    }

    private fun observePostGlobal() = viewModelScope.launch {
        observePostGlobalUseCase.invoke()
            .onStart {
                updateState {
                    copy(
                        loadingPostGlobal = true,
                        errorLoadPostGlobal = null
                    )
                }
            }
            .map {
                Pair(it.id, it)
            }
            .onEach { response ->
                updatePostGlobalOffsetUseCase.invoke(response.first)

                val sortedMap = (state.value.postGlobalMap + response)
                    .toList()
                    .sortedByDescending {
                        it.second.createdAt
                    }
                    .toMap()

                updateState {
                    copy(
                        postGlobalMap = sortedMap,
                        loadingPostGlobal = false
                    )
                }
            }
            .catch { error ->
                updateState {
                    copy(
                        loadingPostGlobal = false,
                        errorLoadPostGlobal = error
                    )
                }
            }
            .collect()
    }
}