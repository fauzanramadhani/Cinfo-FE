package com.ndc.core.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ndc.core.data.constant.PostOptions
import com.ndc.core.data.constant.SharedPref
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SocketHandler @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
) {

    var mSocket: Socket? = null
    var mOptions: IO.Options? = null
    private val baseUrl = "http://${sharedPreferencesManager.getString(SharedPref.SERVER_ADDRESS)}"

    init {
        setSocket()
    }

    @Synchronized
    fun setSocket() {
        try {
            // "http://10.0.2.2:3000" is the network your Android emulator must use to join the localhost network on your computer
            // "http://localhost:3000/" will not work
            // If you want to use your physical phone you could use your ip address plus :3000
            // This will allow your Android Emulator and physical device at your home to connect to the server
            mOptions = IO.Options.builder()
                .setAuth(
                    hashMapOf(
                        PostOptions.POST_GLOBAL_OFFSET to "0"
                    )
                )
                .build()

            mSocket = IO.socket(baseUrl, mOptions)
        } catch (e: Exception) {
            Log.e("SocketIO", e.message.toString())
        }
    }

    inline fun <reified T> observe(
        event: String
    ): Flow<T> = callbackFlow {
        Log.e("test", "1")
        val listener = Emitter.Listener { response ->
            Log.e("test", "2")
            try {
                if (response.isNotEmpty()) {
                    val typeToken = object : TypeToken<T>() {}.type
                    val result: T = Gson().fromJson(response[0].toString(), typeToken)
                    trySend(result)
                }
            } catch (e: Exception) {
                close(e)
                Log.e("error", e.message.toString())
            }
        }

        when (mSocket) {
            null -> close(MSocketException.EmptyServerAddress)
            else -> mSocket?.on(event, listener)
        }


        awaitClose {
            mSocket?.off(event, listener)
        }
    }

    @Synchronized
    fun establishConnection() {
        mSocket?.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket?.disconnect()
    }

}

sealed class MSocketException(message: String) : Exception(message, Throwable(message)) {

    data object EmptyServerAddress : MSocketException("empty_server_address")
}