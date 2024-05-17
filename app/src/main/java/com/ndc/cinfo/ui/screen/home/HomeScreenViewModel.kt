package com.ndc.cinfo.ui.screen.home

import androidx.lifecycle.ViewModel
import com.ndc.core.data.domain.GetFirebaseUserUseCase
import com.ndc.core.data.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getFirebaseUserUseCase: GetFirebaseUserUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    fun firebaseUser() = getFirebaseUserUseCase.invoke()
    fun logout() = logoutUseCase.invoke()
}