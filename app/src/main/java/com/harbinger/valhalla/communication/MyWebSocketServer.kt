package com.harbinger.valhalla.communication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import java.nio.ByteBuffer

/**
 * Created by acorn on 2020/9/24.
 */
class MyWebSocketServer(addr: InetSocketAddress) : WebSocketServer(addr) {
    val TAG = "MyWebSocketServer"
    val message = MutableLiveData<String>()
    lateinit var sender: WebSocket

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
//        Log.d(TAG, "Server onOpen")
        println("Server onOpen")
        if (conn != null) {
            sender = conn
        }
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
//        Log.d(TAG, "Server onClose")
        println("Server onClose")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
//        Log.d(TAG, "Server onMessage $message")
        println("Server onMessage $message")
        this.message.value = message
    }

    override fun onMessage(conn: WebSocket?, message: ByteBuffer?) {
        super.onMessage(conn, message)
//        Log.d(TAG, "Server onMessage ByteBuffer ${message?.let { String(it.array()) }}")
        println("Server onMessage ByteBuffer ${message?.let { String(it.array()) }}")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
//        Log.d(TAG, "Server onError")
        println("Server onError ${conn.toString()}  ex:$ex")
    }

    override fun onStart() {
//        Log.d(TAG, "Server onStart")
        println("Server onStart")
    }

    fun getMessage(): LiveData<String> {
        return message
    }
}