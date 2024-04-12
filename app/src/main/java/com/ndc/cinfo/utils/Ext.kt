package com.ndc.cinfo.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.isEmailInvalid(): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
    return !(emailRegex.matches(this))
}

fun Long.toDateString(pattern: String): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(Date(this))
}

fun String.limitLength(maxLength: Int): String {
    return if (this.length > maxLength) {
        "${this.substring(0, maxLength)}..."
    } else {
        this
    }
}