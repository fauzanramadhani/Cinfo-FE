package com.ndc.cinfo.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.ndc.core.data.domain.RegisterUseCase
import com.ndc.core.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _registerState = MutableStateFlow<UiState<AuthResult>>(UiState.Empty)
    val registerState: StateFlow<UiState<AuthResult>>
        get() = _registerState

    fun register(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _registerState.tryEmit(UiState.Loading)
        registerUseCase.invoke(email, password).addOnSuccessListener { authResult ->
            _registerState.tryEmit(UiState.Success(authResult))
        }.addOnFailureListener {
            _registerState.tryEmit(UiState.Error(it.message.toString()))
        }
    }

    fun clearState() = viewModelScope.launch {
        _registerState.tryEmit(UiState.Empty)
    }
}