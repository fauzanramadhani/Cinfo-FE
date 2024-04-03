package com.ndc.cinfo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ndc.cinfo.ui.navigation.SetupNavHost
import com.ndc.cinfo.ui.theme.CinfoTheme
import com.ndc.cinfo.utils.SocketHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        setContent {
            CinfoTheme {
                val navHostController = rememberNavController()
                SetupNavHost(navHostController = navHostController)
            }
        }
    }

    private fun testSocketIo() {
        val mSocket = SocketHandler.getSocket()
        mSocket.connect()
        Log.e("isActive", mSocket.isActive.toString())
        mSocket.on("chat message") { args ->
            if (args[0] != null) {
                Log.e("Res", args[0].toString())
            }
        }
    }
}