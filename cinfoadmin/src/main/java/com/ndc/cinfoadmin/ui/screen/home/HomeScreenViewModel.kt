package com.ndc.cinfoadmin.ui.screen.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ndc.core.data.base.BaseViewModel
import com.ndc.core.data.constant.SharedPref
import com.ndc.core.data.domain.ObservePostGlobalUseCase
import com.ndc.core.data.domain.UpdatePostGlobalOffsetUseCase
import com.ndc.core.data.domain.UpdateServerAddressUseCase
import com.ndc.core.utils.SharedPreferencesManager
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
    private val sharedPreferencesManager: SharedPreferencesManager,
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
        }
    }

    private fun observePostGlobal() = viewModelScope.launch {
        val baseUrl = "http://${sharedPreferencesManager.getString(SharedPref.SERVER_ADDRESS)}"
        observePostGlobalUseCase.invoke()
            .onStart {
                updateState {
                    copy(
                        loadingPostGlobal = true,
                        errorPostGlobalMessage = null
                    )
                }
            }
            .map {
                Pair(it.id, it)
            }
            .onEach { response ->

                val sortedMap = (state.value.postGlobalMap + response)
                    .toList()
                    .sortedByDescending {
                        it.second.createdAt
                    }
                    .toMap()

                updatePostGlobalOffsetUseCase.invoke(sortedMap.keys.last())

                updateState {
                    copy(
                        postGlobalMap = sortedMap,
                        loadingPostGlobal = false
                    )
                }
            }
            .catch { error ->
                Log.e("vm", error.message.toString())
                updateState {
                    copy(
                        loadingPostGlobal = false,
                        errorPostGlobalMessage = error
                    )
                }
            }
            .collect()
    }
}