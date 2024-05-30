package com.ndc.cinfoadmin.ui.feature.home

import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.domain.ObserveDeletePostGlobal
import com.ndc.core.data.domain.ObserveDeleteRoomUseCase
import com.ndc.core.data.domain.ObservePostGlobalUseCase
import com.ndc.core.data.domain.ObserveRoomUseCase
import com.ndc.core.data.domain.SavePostGlobalCacheUseCase
import com.ndc.core.data.domain.SavePostTypeGlobalUseCase
import com.ndc.core.data.domain.SavePostTypePrivateUseCase
import com.ndc.core.data.domain.SaveRoomCacheUseCase
import com.ndc.core.data.domain.UpdatePostGlobalOffsetUseCase
import com.ndc.core.data.domain.UpdateRoomOffsetUseCase
import com.ndc.core.data.domain.UpdateServerAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val savePostGlobalCacheUseCase: SavePostGlobalCacheUseCase,
    private val observeRoomUseCase: ObserveRoomUseCase,
    private val updateRoomOffsetUseCase: UpdateRoomOffsetUseCase,
    private val saveRoomCacheUseCase: SaveRoomCacheUseCase,
    private val observeDeletePostGlobal: ObserveDeletePostGlobal,
    private val savePostTypeGlobalUseCase: SavePostTypeGlobalUseCase,
    private val savePostTypePrivateUseCase: SavePostTypePrivateUseCase,
    private val observeDeleteRoomUseCase: ObserveDeleteRoomUseCase,
) : BaseViewModel<HomeState, HomeAction, HomeEffect>(
    HomeState()
) {

    init {
        observePostGlobal()
        observeRoom()
        observeDeletePostGlobal()
        observeDeleteRoom()
    }

    override fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnUpdateServerDialogShowChange -> updateState {
                copy(
                    updateServerDialogShow = action.show
                )
            }

            is HomeAction.OnUpdateServerTvChange -> updateState {
                copy(updateServerTvValue = action.value)
            }

            is HomeAction.OnUpdateServer -> updateServerAddressUseCase.invoke(state.value.updateServerTvValue)
            is HomeAction.OnItemPostGlobalClicked -> {
                savePostGlobalCacheUseCase.invoke(action.post)
                sendEffect(HomeEffect.OnItemPostClicked)
            }

            is HomeAction.OnItemRoomClicked -> {
                saveRoomCacheUseCase.invoke(action.room)
                sendEffect(HomeEffect.OnItemRoomClicked)
            }

            HomeAction.OnSavePostTypeGlobal -> savePostTypeGlobalUseCase.invoke()
            HomeAction.OnSavePostTypePrivate -> savePostTypePrivateUseCase.invoke()
        }
    }

    private fun observePostGlobal() = viewModelScope.launch {

        launch {
            delay(3000)
            if (state.value.postGlobalResponseMap == null && state.value.error == null) {
                updateState { copy(postGlobalResponseMap = mapOf()) }
            }
        }

        observePostGlobalUseCase.invoke()
            .onStart {
                updateState { copy(loading = true) }
            }
            .map {
                Pair(it.id, it)
            }
            .onEach { response ->
                updatePostGlobalOffsetUseCase.invoke(response.second.clientOffset.toString())

                val updatedData =
                    if (state.value.postGlobalResponseMap == null) mapOf(response)
                    else (state.value.postGlobalResponseMap!! + response)
                        .toList()
                        .sortedByDescending {
                            it.second.createdAt
                        }
                        .toMap()
                updateState {
                    copy(
                        postGlobalResponseMap = updatedData,
                        loading = false
                    )
                }
            }
            .catch { error ->
                updateState {
                    copy(
                        loading = false,
                        error = error,

                        )
                }
            }
            .collect()
    }

    private fun observeDeletePostGlobal() = viewModelScope.launch {
        observeDeletePostGlobal.invoke()
            .onStart {
                updateState { copy(loading = true) }
            }
            .onEach {
                val mutableMap = state.value.postGlobalResponseMap?.toMutableMap()
                mutableMap?.remove(it)
                updateState {
                    copy(
                        loading = false,
                        postGlobalResponseMap = mutableMap
                    )
                }
            }
            .catch {
                updateState { copy(error = it) }
            }
            .collect()
    }

    private fun observeRoom() = viewModelScope.launch {
        launch {
            delay(3000)
            if (state.value.roomMap == null && state.value.error == null) {
                updateState { copy(roomMap = mapOf()) }
            }
        }
        observeRoomUseCase.invoke()
            .onStart {
                updateState { copy(loading = true) }
            }
            .map {
                Pair(it.id, it)
            }
            .onEach { response ->
                updateRoomOffsetUseCase.invoke(response.second.clientOffset.toString())

                val updatedMap =
                    if (state.value.roomMap == null) mapOf(response)
                    else (state.value.roomMap!! + response)
                        .toList()
                        .sortedByDescending {
                            it.second.createdAt
                        }
                        .toMap()
                updateState {
                    copy(
                        roomMap = updatedMap,
                        loading = false
                    )
                }
            }
            .catch { error ->
                updateState {
                    copy(
                        loading = false,
                        error = error
                    )
                }
            }
            .collect()
    }

    private fun observeDeleteRoom() = viewModelScope.launch {
        observeDeleteRoomUseCase.invoke()
            .onStart {
                updateState { copy(loading = true) }
            }
            .onEach {
                val updatedRoomMap = state.value.roomMap?.toMutableMap()
                updatedRoomMap?.remove(it)
                updateState { copy(roomMap = updatedRoomMap) }
            }
            .catch {
                updateState { copy(error = it) }
            }
            .collect()
    }
}