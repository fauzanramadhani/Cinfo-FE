package com.ndc.cinfo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ndc.cinfo.ui.theme.CinfoTheme
import com.ndc.cinfo.utils.SocketHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        setContent {
            CinfoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    testSocketIo()
                }
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CinfoTheme {
        Greeting("Android")
    }
}