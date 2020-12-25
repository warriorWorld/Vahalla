package com.harbinger.valhalla.listener

/**
 * Created by acorn on 2020/12/25.
 */
interface CommunicatorListener {
    fun onGetMessage(message: String)
    fun onDisconnected()
}