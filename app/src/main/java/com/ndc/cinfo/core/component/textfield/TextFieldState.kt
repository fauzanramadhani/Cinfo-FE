package com.ndc.cinfo.core.component.textfield

sealed interface TextFieldState {
    data object Empty: TextFieldState
    data class Error(val errorMessage: String): TextFieldState
}