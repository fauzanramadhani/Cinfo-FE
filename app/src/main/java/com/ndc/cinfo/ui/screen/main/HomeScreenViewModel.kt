package com.ndc.cinfo.ui.screen.main

import androidx.lifecycle.ViewModel
import com.ndc.cinfo.data.authentication.domain.GetFirebaseUserUseCase
import com.ndc.cinfo.data.authentication.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getFirebaseUserUseCase: GetFirebaseUserUseCase,
    private val logoutUseCase: LogoutUseCase,
): ViewModel() {
    fun firebaseUser() = getFirebaseUserUseCase.invoke()
    fun logout() = logoutUseCase.invoke()
}