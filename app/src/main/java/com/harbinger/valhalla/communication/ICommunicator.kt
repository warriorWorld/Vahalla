package com.harbinger.valhalla.communication

import androidx.lifecycle.LiveData
import com.harbinger.valhalla.bean.MessageBean

/**
 * Created by acorn on 2020/12/24.
 */
interface ICommunicator {
    fun sendMessage(message: String)
    fun sendMessage(message: MessageBean)
    fun setOnReceiveMessageListener(onReceiveMessageListener: OnReceiveMessageListener)
    fun release()
}

interface OnReceiveMessageListener {
    fun onReceiveMessage(message: String, details: String?=null)
}