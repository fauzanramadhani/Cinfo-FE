package com.ndc.core.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ndc.core.BuildConfig
import com.ndc.core.R
import com.ndc.core.data.constant.PostOptions
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketHandler(
    val context: Context
) {

    private lateinit var mSocket: Socket
    private lateinit var mOptions: IO.Options

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
            mSocket = IO.socket(BuildConfig.BASE_URL, mOptions)
        } catch (e: URISyntaxException) {
            Log.e("SocketIO", e.message.toString())
        }
    }

    inline fun <reified T> saveObserve(
        event: String,
        crossinline onSuccess: (data: T) -> Unit,
        crossinline onFailure: (message: String) -> Unit
    ) {
        try {
            getSocket().on(event) { response ->
                if (!response.isNullOrEmpty()) {
                    try {
                        val type = object : TypeToken<T>() {}.type
                        val result: T = Gson().fromJson(response[0].toString(), type)
                        onSuccess(result)
                    } catch (e: Exception) {
                        Log.e("1", e.message.toString())
                        onFailure(context.getString(R.string.server_error))
                    }
                }

            }
        } catch (e: Exception) {
            onFailure(context.getString(R.string.internal_error))
            Log.e("2", e.message.toString())
        }
    }


    @Synchronized
    fun getOptions(): IO.Options = mOptions

    @Synchronized
    fun getSocket(): Socket = mSocket

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}