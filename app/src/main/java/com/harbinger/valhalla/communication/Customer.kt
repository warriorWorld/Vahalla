package com.harbinger.valhalla.communication

import androidx.lifecycle.LiveData
import com.harbinger.valhalla.listener.ISender

/**
 * Created by acorn on 2020/12/20.
 */
class Customer : ISender {
    override fun connect(url: String) {
        SocketManager.url = url
    }

    override fun sendMessage(message: String) {
        SocketManager.instance.send(message)
    }

    override fun getMessage(): LiveData<String> {
        return SocketManager.message
    }

    override fun release() {
        SocketManager.instance.close(0, null)
    }
}