package com.ndc.cinfoadmin.ui.feature.eachroom

import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.domain.EmitDeleteMemberUseCase
import com.ndc.core.data.domain.EmitDeleteRoomUseCase
import com.ndc.core.data.domain.EmitMemberUseCase
import com.ndc.core.data.domain.GetRoomCacheUseCase
import com.ndc.core.data.domain.ObserveDeleteMemberUseCase
import com.ndc.core.data.domain.ObserveDeletePostPrivateUseCase
import com.ndc.core.data.domain.ObserveMemberUseCase
import com.ndc.core.data.domain.ObservePostPrivateUseCase
import com.ndc.core.data.domain.SavePostPrivateCacheUseCase
import com.ndc.core.data.domain.UpdateMemberOffsetUseCase
import com.ndc.core.data.domain.UpdatePostPrivateOffset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class EachRoomViewModel @Inject constructor(
    private val getRoomCacheUseCase: GetRoomCacheUseCase,
    private val observePostPrivateUseCase: ObservePostPrivateUseCase,
    private val updatePostPrivateOffset: UpdatePostPrivateOffset,
    private val observeDeletePostPrivateUseCase: ObserveDeletePostPrivateUseCase,
    private val savePostPrivateCacheUseCase: SavePostPrivateCacheUseCase,
    private val emitDeleteRoomUseCase: EmitDeleteRoomUseCase,
    private val observeMemberUseCase: ObserveMemberUseCase,
    private val emitMemberUseCase: EmitMemberUseCase,
    private val emitDeleteMemberUseCase: EmitDeleteMemberUseCase,
    private val observeDeleteMemberUseCase: ObserveDeleteMemberUseCase,
    private val updateMemberOffsetUseCase: UpdateMemberOffsetUseCase
) : BaseViewModel<EachRoomState, EachRoomAction, EachRoomEffect>(EachRoomState()) {

    init {
        getRoomCache()
        observePrivatePost()
        observeDeletePostPrivate()
        observeMember()
        observeDeleteMember()
    }

    override fun onAction(action: EachRoomAction) {
        when (action) {
            is EachRoomAction.OnScreenChange -> updateState { copy(currentContent = action.screen) }
            is EachRoomAction.OnPostPrivateClicked -> {
                savePostPrivateCacheUseCase.invoke(action.postPrivateResponse)
                sendEffect(EachRoomEffect.OnNavigateToPost)
            }

            EachRoomAction.OnDeleteRoom -> emitDeleteRoom()
            EachRoomAction.OnReloadRoomCache -> getRoomCache()
            is EachRoomAction.OnMemberClicked -> updateState { copy(selectedMember = action.member) }
            is EachRoomAction.OnShowSheet -> updateState { copy(sheetType = action.sheetType) }
            is EachRoomAction.OnEmitMember -> emitMember()
            is EachRoomAction.OnDeleteMember -> emitDeleteMember()
            is EachRoomAction.OnEmailEmitMemberValueChange -> updateState {
                copy(
                    emailEmitMemberValue = action.value
                )
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun emitMember() = viewModelScope.launch {
        emitMemberUseCase
            .invoke(state.value.roomId, state.value.emailEmitMemberValue)
            .take(1)
            .onStart {
                updateState { copy(loadingEmit = true) }
            }
            .onEach {
                sendEffect(EachRoomEffect.OnEmitMemberSuccess)
                updateState { copy(emailEmitMemberValue = "") }
            }
            .timeout(3000.milliseconds)
            .catch {
                sendEffect(EachRoomEffect.OnShowToast(it.message.toString()))
            }
            .onCompletion {
                updateState { copy(loadingEmit = false) }
                delay(1000)
                sendEffect(EachRoomEffect.None)

            }
            .collect()
    }

    private fun observeMember() = viewModelScope.launch {
        launch {
            delay(3000)
            if (state.value.members == null) {
                updateState {
                    copy(
                        members = mapOf(),
                        loadingObserve = false
                    )
                }
            }
        }

        observeMemberUseCase.invoke(state.value.roomId)
            .onStart {
                updateState { copy(loadingObserve = true) }
            }
            .map {
                Pair(it.id, it)
            }
            .onEach { response ->
                updateMemberOffsetUseCase.invoke(response.second.clientOffset.toString())
                val updatedData =
                    if (state.value.members == null) mapOf(response)
                    else (state.value.members!! + response)
                        .toList()
                        .sortedByDescending {
                            it.second.createdAt
                        }
                        .toMap()
                updateState { copy(members = updatedData) }
            }
            .catch {
                sendEffect(EachRoomEffect.OnShowToast(it.message.toString()))
            }
            .onCompletion {
                delay(1000)
                sendEffect(EachRoomEffect.None)
            }
            .collect()
    }

    private fun observeDeleteMember() = viewModelScope.launch {
        observeDeleteMemberUseCase
            .invoke(state.value.roomId)
            .onStart {
                updateState { copy(loadingObserve = true) }
            }
            .onEach {
                val newMembers = state.value.members?.toMutableMap()
                newMembers?.remove(it)
                updateState {
                    copy(
                        loadingObserve = false,
                        members = newMembers
                    )
                }
            }
            .catch {
                sendEffect(EachRoomEffect.OnShowToast(it.message.toString()))
            }
            .onCompletion {
                delay(1000)
                sendEffect(EachRoomEffect.None)
            }
            .collect()
    }

    @OptIn(FlowPreview::class)
    private fun emitDeleteMember() = viewModelScope.launch {
        emitDeleteMemberUseCase
            .invoke(
                memberId = state.value.selectedMember?.id ?: "",
                roomId = state.value.roomId
            )
            .take(1)
            .onStart {
                updateState { copy(loadingEmit = true) }
            }
            .onEach {
                sendEffect(EachRoomEffect.OnDeleteMemberSuccess)
            }
            .timeout(3000.milliseconds)
            .catch {
                sendEffect(EachRoomEffect.OnShowToast(it.message.toString()))
            }
            .onCompletion {
                updateState { copy(loadingEmit = false) }
                delay(1000)
                sendEffect(EachRoomEffect.None)
            }
            .collect()
    }

    private fun getRoomCache() {
        val roomCache = getRoomCacheUseCase.invoke()
        updateState {
            copy(
                roomId = roomCache.id,
                roomName = roomCache.roomName,
                additional = roomCache.additional
            )
        }
    }

    @OptIn(FlowPreview::class)
    private fun emitDeleteRoom() = viewModelScope.launch {
        emitDeleteRoomUseCase.invoke(state.value.roomId)
            .take(1)
            .onStart {
                updateState { copy(loadingObserve = true) }
            }
            .timeout(3000.milliseconds)
            .onEach {
                sendEffect(EachRoomEffect.OnDeleteRoomSuccess)
            }
            .catch {
                sendEffect(EachRoomEffect.OnShowToast(it.message.toString()))
            }
            .onCompletion {
                updateState { copy(loadingObserve = false) }
                delay(1000)
                sendEffect(EachRoomEffect.None)
            }
            .collect()
    }

    private fun observePrivatePost() = viewModelScope.launch {
        launch {
            delay(3000)
            if (state.value.postPrivate == null) {
                updateState {
                    copy(
                        postPrivate = mapOf(),
                        loadingObserve = false
                    )
                }
            }
        }

        observePostPrivateUseCase.invoke(state.value.roomId)
            .onStart {
                updateState { copy(loadingObserve = true) }
            }
            .map {
                Pair(it.id, it)
            }
            .onEach { response ->
                updatePostPrivateOffset.invoke(response.second.clientOffset.toString())
                val updatedData =
                    if (state.value.postPrivate == null) mapOf(response)
                    else (state.value.postPrivate!! + response)
                        .toList()
                        .sortedByDescending {
                            it.second.createdAt
                        }
                        .toMap()
                updateState { copy(postPrivate = updatedData) }
            }
            .catch {
                sendEffect(EachRoomEffect.OnShowToast(it.message.toString()))
            }
            .onCompletion {
                delay(1000)
                sendEffect(EachRoomEffect.None)
            }
            .collect()
    }

    private fun observeDeletePostPrivate() = viewModelScope.launch {
        observeDeletePostPrivateUseCase
            .invoke(state.value.roomId)
            .onStart {
                updateState { copy(loadingObserve = true) }
            }
            .onEach {
                val newPostPrivate = state.value.postPrivate?.toMutableMap()
                newPostPrivate?.remove(it)
                updateState {
                    copy(
                        loadingObserve = false,
                        postPrivate = newPostPrivate
                    )
                }
            }
            .catch {
                sendEffect(EachRoomEffect.OnShowToast(it.message.toString()))
            }
            .onCompletion {
                delay(1000)
                sendEffect(EachRoomEffect.None)
            }
            .collect()
    }
}