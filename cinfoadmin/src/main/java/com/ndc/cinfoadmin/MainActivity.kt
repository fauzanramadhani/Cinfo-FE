package com.ndc.cinfoadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ndc.cinfoadmin.ui.navigation.SetupNavHost
import com.ndc.core.ui.theme.CinfoTheme
import com.ndc.core.utils.SocketHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mSocketHandler = SocketHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            CinfoTheme {
                SetupNavHost(navHostController = navHostController)
            }
        }
    }

//    private fun testSocketIo() {
//        val mOptions = socketHandler.getOptions()
//        socketHandler.saveObserve<PostGlobalResponse>(
//            event = PostEvent.POST_GLOBAL,
//            onSuccess = { response ->
//                if (response.clientOffset >
//                    (mOptions.auth[PostOptions.POST_GLOBAL_OFFSET]?.toInt() ?: 0)
//                ) {
//                    mOptions.auth[PostOptions.POST_GLOBAL_OFFSET] = response.clientOffset.toString()
//                    Log.e("success", response.toString())
//                }
//            },
//            onFailure = {
//                Log.e("error", it)
//            }
//        )
//    }
}