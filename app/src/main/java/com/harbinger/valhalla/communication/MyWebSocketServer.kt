package com.harbinger.valhalla.communication

import com.harbinger.valhalla.listener.MessageListener
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
    lateinit var messageListener: MessageListener
    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
//        Log.d(TAG, "Server onOpen")
        println("Server onOpen")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
//        Log.d(TAG, "Server onClose")
        println("Server onClose")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
//        Log.d(TAG, "Server onMessage $message")
        println("Server onMessage $message")
        messageListener.onMessage(message)
        conn?.send("server $message")
    }

    override fun onMessage(conn: WebSocket?, message: ByteBuffer?) {
        super.onMessage(conn, message)
//        Log.d(TAG, "Server onMessage ByteBuffer ${message?.let { String(it.array()) }}")
        println("Server onMessage ByteBuffer ${message?.let { String(it.array()) }}")
        conn?.send("....")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
//        Log.d(TAG, "Server onError")
        println("Server onError ${conn.toString()}  ex:$ex")
    }

    override fun onStart() {
//        Log.d(TAG, "Server onStart")
        println("Server onStart")
    }
}