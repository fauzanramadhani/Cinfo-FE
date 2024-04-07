package com.ndc.cinfo.ui.screen.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthResult
import com.ndc.cinfo.core.data.authentication.domain.HandleLoginWithGoogleUseCase
import com.ndc.cinfo.core.data.authentication.domain.LoginBasicUseCase
import com.ndc.cinfo.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginBasicUseCase: LoginBasicUseCase,
    private val handleLoginWithGoogleUseCase: HandleLoginWithGoogleUseCase,
    private val googleSignInClient: GoogleSignInClient,

    ) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<AuthResult>>(UiState.Empty)
    val loginState: StateFlow<UiState<AuthResult>>
        get() = _loginState
    private val _loginWithGoogleState = MutableStateFlow<UiState<AuthResult>>(UiState.Empty)
    val loginWithGoogleState: StateFlow<UiState<AuthResult>>
        get() = _loginWithGoogleState

    fun loginBasic(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginState.tryEmit(UiState.Loading)
        loginBasicUseCase.invoke(email, password).addOnSuccessListener { authResult ->
            _loginState.tryEmit(UiState.Success(authResult))
        }.addOnFailureListener {
            _loginState.tryEmit(UiState.Error(it.message.toString()))
        }
    }

    fun loginWithGoogleIntent(): Intent {
        _loginWithGoogleState.tryEmit(UiState.Loading)
        return googleSignInClient.signInIntent
    }

    fun handleLoginWithGoogle(intent: Intent) = viewModelScope.launch {
        try {
            handleLoginWithGoogleUseCase.invoke(intent).addOnSuccessListener {
                _loginWithGoogleState.tryEmit(UiState.Success(it))
            }.addOnFailureListener {
                _loginWithGoogleState.tryEmit(UiState.Error(it.message.toString()))
            }
        } catch (e: Exception) {
            _loginWithGoogleState.tryEmit(
                when (e.message.toString()) {
                    "12501: " -> UiState.Error("Operation Canceled by User")
                    else -> UiState.Error(e.message.toString())
                }
            )
        }
    }
}