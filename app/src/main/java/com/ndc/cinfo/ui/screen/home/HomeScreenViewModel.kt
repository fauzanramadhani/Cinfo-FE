package com.ndc.cinfo.ui.screen.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.constant.Event
import com.ndc.core.data.datasource.remote.response.PostPrivateResponse
import com.ndc.core.data.domain.GetFirebaseUserUseCase
import com.ndc.core.data.domain.LogoutUseCase
import com.ndc.core.data.domain.ObservePostGlobalUseCase
import com.ndc.core.data.domain.ObserveUserRoomUseCase
import com.ndc.core.data.domain.SavePostGlobalCacheUseCase
import com.ndc.core.data.domain.SavePostPrivateCacheUseCase
import com.ndc.core.data.domain.SavePostTypeGlobalUseCase
import com.ndc.core.data.domain.SavePostTypePrivateUseCase
import com.ndc.core.data.domain.UpdatePostGlobalOffsetUseCase
import com.ndc.core.data.domain.UpdatePostPrivateOffset
import com.ndc.core.data.domain.UpdateServerAddressUseCase
import com.ndc.core.utils.SocketHandler
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
    private val getFirebaseUserUseCase: GetFirebaseUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val observePostGlobalUseCase: ObservePostGlobalUseCase,
    private val updatePostGlobalOffsetUseCase: UpdatePostGlobalOffsetUseCase,
    private val savePostGlobalCacheUseCase: SavePostGlobalCacheUseCase,
    private val updateServerAddressUseCase: UpdateServerAddressUseCase,
    private val observeUserRoomUseCase: ObserveUserRoomUseCase,
    private val updatePostPrivateOffset: UpdatePostPrivateOffset,
    private val mSocketHandler: SocketHandler,
    private val savePostPrivateCacheUseCase: SavePostPrivateCacheUseCase,
    private val savePostTypePrivateUseCase: SavePostTypePrivateUseCase,
    private val savePostTypeGlobalUseCase: SavePostTypeGlobalUseCase
) : BaseViewModel<HomeState, HomeAction, HomeEffect>(HomeState()) {

    init {
        updateState {
            copy(
                firebaseUser = getFirebaseUserUseCase.invoke()
            )
        }
        observePostGlobal()
        observeUserRoom()

    }

    override fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnUpdateServerDialogShowChange -> updateState {
                copy(
                    updateServerDialogShow = action.show
                )
            }

            is HomeAction.OnContentChange -> updateState { copy(content = action.content) }
            HomeAction.OnLogout -> logoutUseCase.invoke()
            is HomeAction.OnItemPostGlobalClicked -> {
                savePostGlobalCacheUseCase.invoke(action.post)
                savePostTypeGlobalUseCase.invoke()
                sendEffect(HomeEffect.OnNavigateToDetailPostScreen)
            }

            is HomeAction.OnUpdateServerTvChange -> updateState {
                copy(updateServerTvValue = action.value)
            }

            is HomeAction.OnUpdateServer -> updateServerAddressUseCase.invoke(state.value.updateServerTvValue)
            is HomeAction.OnObservePostPrivate -> observePrivatePost(action.roomId)
            is HomeAction.OnItemPostPrivateClicked -> {
                savePostPrivateCacheUseCase.invoke(action.post)
                savePostTypePrivateUseCase.invoke()
                sendEffect(HomeEffect.OnNavigateToDetailPostScreen)
            }
        }
    }

    private fun observeUserRoom() = viewModelScope.launch {

        observeUserRoomUseCase.invoke()
            .onStart {
                updateState { copy(loading = true) }
            }
            .onEach { roomResponse ->
                updateState { copy(room = roomResponse) }
                roomResponse?.let {
                    observePrivatePost(it.id)
                    observeDeletePostPrivate(it.id)
                }
            }
            .catch {
                updateState { copy(error = it) }
            }
            .collect()
    }

    private fun observePostGlobal() = viewModelScope.launch {

        launch {
            delay(3000)
            if (state.value.postGlobalMap == null && state.value.error == null) {
                updateState { copy(postGlobalMap = mapOf()) }
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
                    if (state.value.postGlobalMap == null) mapOf(response)
                    else (state.value.postGlobalMap!! + response)
                        .toList()
                        .sortedByDescending {
                            it.second.createdAt
                        }
                        .toMap()
                updateState {
                    copy(
                        postGlobalMap = updatedData,
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

    private fun observePrivatePost(roomId: String) = viewModelScope.launch {
        try {
            mSocketHandler.establishConnection()
            mSocketHandler.mSocket?.on(roomId + Event.POST_PRIVATE) { privatePostResponse ->
                val result = Gson().fromJson(
                    privatePostResponse[0]?.toString(),
                    PostPrivateResponse::class.java
                )
                updatePostPrivateOffset.invoke(result.clientOffset.toString())
                val updatedData =
                    if (state.value.postPrivateMap == null) mapOf(result.id to result)
                    else (state.value.postPrivateMap!! + Pair(result.id, result))
                        .toList()
                        .sortedByDescending {
                            it.second.createdAt
                        }
                        .toMap()
                updateState { copy(postPrivateMap = updatedData) }
            }
        } catch (e: Exception) {
            updateState {
                copy(
                    loading = false,
                    error = e,
                )
            }
        }
    }

    private fun observeDeletePostPrivate(roomId: String) = viewModelScope.launch {
        launch {
            delay(3000)
            if (state.value.postPrivateMap == null && state.value.error == null) {
                updateState { copy(postPrivateMap = mapOf()) }
            }
        }

        try {
            mSocketHandler.establishConnection()
            mSocketHandler.mSocket?.on(roomId + Event.ON_DELETE_POST_PRIVATE) {
                val removedPrivatePostId = it[0].toString()
                val newPostPrivate = state.value.postPrivateMap?.toMutableMap()
                newPostPrivate?.remove(removedPrivatePostId)
                updateState {
                    copy(
                        loading = false,
                        postPrivateMap = newPostPrivate
                    )
                }
            }
        } catch (e: Exception) {
            sendEffect(HomeEffect.OnShowToast(e.message.toString()))
            delay(1000)
            sendEffect(HomeEffect.None)
        }
    }
}