package com.harbinger.valhalla.communication

import android.util.Log
import com.google.gson.Gson
import com.harbinger.valhalla.bean.MessageBean
import com.harbinger.valhalla.listener.CommunicatorListener

/**
 * Created by acorn on 2020/12/20.
 */
class Customer : ISender {
    private val TAG="Customer"
    private val gson = Gson()
    private var onReceiveMessageListener: OnReceiveMessageListener? = null

    override fun connect(url: String) {
        SocketManager.url = url
        SocketManager.communicatorListener = object : CommunicatorListener {
            override fun onGetMessage(message: String) {
                val messageBean = gson.fromJson(message, MessageBean::class.java)
                if (null != messageBean) {
                    onReceiveMessageListener?.onReceiveMessage(messageBean.msg, messageBean.details)
                } else {
                    onReceiveMessageListener?.onReceiveMessage(message)
                }
            }

            override fun onDisconnected() {
                onReceiveMessageListener?.onReceiveMessage(Message.DISCONNECTED)
            }
        }
    }

    override fun sendMessage(message: String) {
        Log.d(TAG,"send message:$message")
        SocketManager.instance.send(message)
    }

    override fun sendMessage(message: MessageBean) {
        SocketManager.instance.send(gson.toJson(message))
    }

    override fun setOnReceiveMessageListener(onReceiveMessageListener: OnReceiveMessageListener) {
        this.onReceiveMessageListener = onReceiveMessageListener
    }

    override fun release() {
        SocketManager.instance.close(0, null)
    }
}