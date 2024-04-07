package com.ndc.cinfo.utils

import android.content.Context
import android.widget.Toast

class Toast(
    private val context: Context,
    private val message: String
) {
    fun short() {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    fun long() {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}