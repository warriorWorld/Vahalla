package com.harbinger.valhalla.listener

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

/**
 * Created by acorn on 2020/9/12.
 */
class MySocketListener : WebSocketListener() {
    private val TAG = "MySocketListener"
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        // 有客户端连接时回调
//        Log.d(TAG, "client onOpen")
        println("client onOpen")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        // 收到新消息时回调
//        Log.d(TAG, "client onMessage:$text")
        println("client onMessage:$text")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        // 客户端主动关闭时回调
//        Log.d(TAG, "client onClosing code:$code,reason:$reason")
        println("client onClosing code:$code,reason:$reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        // WebSocket 连接关闭
//        Log.d(TAG, "client onClosed code:$code,reason:$reason")
        println("client onClosed code:$code,reason:$reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        // 出错了
//        Log.d(TAG, "client onFailure err:${t.message}")
        println("client onFailure err:${t.message}")
    }
}