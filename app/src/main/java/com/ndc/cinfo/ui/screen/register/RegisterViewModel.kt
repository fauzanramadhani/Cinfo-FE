package com.ndc.cinfo.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.ndc.core.data.domain.RegisterUseCase
import com.ndc.core.data.domain.SaveAuthUseCase
import com.ndc.core.data.domain.UpdateServerAddressUseCase
import com.ndc.core.utils.SharedPreferencesManager
import com.ndc.core.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val saveAuthUseCase: SaveAuthUseCase,

    ) : ViewModel() {
    private val _registerState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val registerState: StateFlow<UiState<String>>
        get() = _registerState

    fun register(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _registerState.tryEmit(UiState.Loading)
        registerUseCase.invoke(email, password).addOnSuccessListener { _ ->
            saveAuth(email)
        }.addOnFailureListener {
            _registerState.tryEmit(UiState.Error(it.message.toString()))
        }
    }

    fun clearState() = viewModelScope.launch {
        _registerState.tryEmit(UiState.Empty)
    }

    private fun saveAuth(email: String) = viewModelScope.launch {
        saveAuthUseCase.invoke(email)
            .onEach {
                _registerState.tryEmit(UiState.Success(email))
            }
            .catch {
                _registerState.tryEmit(UiState.Error(it.message.toString()))
            }
            .collect()
    }
}