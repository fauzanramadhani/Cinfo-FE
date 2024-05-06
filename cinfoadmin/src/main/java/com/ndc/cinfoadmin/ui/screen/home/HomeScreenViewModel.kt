package com.ndc.cinfoadmin.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ndc.core.data.domain.ObservePostGlobalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    observePostGlobalUseCase: ObservePostGlobalUseCase
) : ViewModel() {

    init {
        observePostGlobalUseCase.invoke(
            onSuccess = { response ->
                Log.e("Success", response.toString())
            },
            onFailure = { error ->
                Log.e("Error", error)
            }
        )
    }
}