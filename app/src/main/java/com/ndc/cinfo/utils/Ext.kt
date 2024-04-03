package com.ndc.cinfo.utils

fun String.isEmailInvalid(): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
    return !(emailRegex.matches(this))
}