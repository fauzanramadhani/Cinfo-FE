package com.ndc.core.utils

sealed interface Status<out T> {
    data class Success<out T>(val data: T) : Status<T>
    data class Error(val message: String) : Status<Nothing>
}