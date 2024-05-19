package com.ndc.core.data.repository

import com.ndc.core.data.constant.SharedPref
import com.ndc.core.utils.SharedPreferencesManager
import javax.inject.Inject

class ServerConfigRepository @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    fun updateServerAddress(address: String) =
        sharedPreferencesManager.saveString(SharedPref.SERVER_ADDRESS, address)
}