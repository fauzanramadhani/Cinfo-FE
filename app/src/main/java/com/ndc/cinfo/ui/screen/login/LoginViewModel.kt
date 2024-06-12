package com.ndc.cinfo.ui.screen.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ndc.core.data.domain.HandleLoginWithGoogleUseCase
import com.ndc.core.data.domain.LoginBasicUseCase
import com.ndc.core.data.domain.SaveAuthUseCase
import com.ndc.core.data.domain.UpdateServerAddressUseCase
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
class LoginViewModel @Inject constructor(
    private val loginBasicUseCase: LoginBasicUseCase,
    private val handleLoginWithGoogleUseCase: HandleLoginWithGoogleUseCase,
    private val googleSignInClient: GoogleSignInClient,
    private val updateServerAddressUseCase: UpdateServerAddressUseCase,
    private val saveAuthUseCase: SaveAuthUseCase,
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val loginState: StateFlow<UiState<String>>
        get() = _loginState

    fun loginBasic(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginState.tryEmit(UiState.Loading)
        loginBasicUseCase.invoke(email, password).addOnSuccessListener { _ ->
            saveAuth(email)
        }.addOnFailureListener {
            _loginState.tryEmit(UiState.Error(it.message.toString()))
        }
    }

    fun loginWithGoogleIntent(): Intent {
        _loginState.tryEmit(UiState.Loading)
        return googleSignInClient.signInIntent
    }

    fun handleLoginWithGoogle(intent: Intent) = viewModelScope.launch {
        try {
            handleLoginWithGoogleUseCase.invoke(intent).addOnSuccessListener {
                it.user?.email?.let { firebaseEmail ->
                    saveAuth(firebaseEmail)
                }
                Log.e("email", it.user?.email.toString())
            }.addOnFailureListener {
                _loginState.tryEmit(UiState.Error(it.message.toString()))
            }
        } catch (e: Exception) {
            _loginState.tryEmit(
                when (e.message.toString()) {
                    "12501: " -> UiState.Error("Operation Canceled by User")
                    else -> UiState.Error(e.message.toString())
                }
            )
        }
    }

    fun clearState() = viewModelScope.launch {
        _loginState.tryEmit(UiState.Empty)
    }

    fun updateServerAddress(email: String) = updateServerAddressUseCase.invoke(email)

    private fun saveAuth(email: String) = viewModelScope.launch {
        saveAuthUseCase.invoke(email)
            .onEach {
                _loginState.tryEmit(UiState.Success(email))
            }
            .catch {
                _loginState.tryEmit(UiState.Error(it.message.toString()))
            }
            .collect()
    }
}