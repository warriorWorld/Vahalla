package com.harbinger.valhalla.communication

import com.harbinger.valhalla.listener.CommunicatorListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

/**
 * Created by acorn on 2020/9/12.
 */
class SocketManager private constructor() {
    private val TAG = "SocketManager"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) //连接超时
        .pingInterval(60L, TimeUnit.SECONDS) //心跳间隔
        .retryOnConnectionFailure(true)
        .addInterceptor { chain ->
            val request = chain.request()
//            Log.d(TAG, "Interceptor 执行了? ${request.url()}")
            println("Interceptor 执行了? ${request.url()}")
            chain.proceed(request)
        }
        .build()

    companion object {
        lateinit var url: String
        lateinit var communicatorListener: CommunicatorListener
        val instance: WebSocket by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SocketManager().getSocket() }
    }

    private fun getSocket(): WebSocket {
        val request = Request.Builder()
            .url(url)
            .header("token", "mockToken")
            .header("userId", "acorn")
            .build()
        return client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                communicatorListener.onGetMessage(text)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                communicatorListener.onDisconnected()
            }
        })
    }
}