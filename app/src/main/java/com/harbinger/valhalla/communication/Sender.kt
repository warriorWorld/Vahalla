package com.harbinger.valhalla.communication

import com.harbinger.valhalla.listener.ISender

/**
 * Created by acorn on 2020/12/20.
 */
class Sender : ISender {
    override fun setUrl(url: String) {
        SocketManager.url = url
    }

    override fun sendMessage(message: String) {
        SocketManager.instance.send(message)
    }
}