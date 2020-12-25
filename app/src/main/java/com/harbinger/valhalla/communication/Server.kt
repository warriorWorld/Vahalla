package com.harbinger.valhalla.communication

import android.util.Log
import com.google.gson.Gson
import com.harbinger.valhalla.bean.MessageBean
import com.harbinger.valhalla.listener.CommunicatorListener
import com.harbinger.valhalla.listener.ServerInitListener
import com.harbinger.valhalla.utils.NetUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.InetAddress
import java.net.InetSocketAddress

/**
 * Created by acorn on 2020/12/20.
 */
class Server : IReceiver {
    private val TAG = "Server"
    private var disposable: Disposable? = null
    private var server: MyWebSocketServer? = null
    private val gson = Gson()
    private var onReceiveMessageListener: OnReceiveMessageListener? = null

    override fun sendMessage(message: String) {
        server?.sender?.send(message)
    }

    override fun sendMessage(message: MessageBean) {
        server?.sender?.send(gson.toJson(message))
    }

    override fun setOnReceiveMessageListener(onReceiveMessageListener: OnReceiveMessageListener) {
        this.onReceiveMessageListener = onReceiveMessageListener
    }

    override fun initReceiver(listener: ServerInitListener) {
        val port = 43497
        disposable = Observable.create<InetAddress> {
            it.onNext(NetUtils.getLocalIPAddress())
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                server = MyWebSocketServer(InetSocketAddress(it, port))
                server?.communicatorListener = object : CommunicatorListener {
                    override fun onGetMessage(message: String) {
                        Log.d(TAG, "onGetMessage:$message")
                        val messageBean = gson.fromJson(message, MessageBean::class.java)
                        if (null != messageBean) {
                            onReceiveMessageListener?.onReceiveMessage(
                                messageBean.msg,
                                messageBean.details
                            )
                        } else {
                            onReceiveMessageListener?.onReceiveMessage(message)
                        }
                    }

                    override fun onDisconnected() {
                        Log.d(TAG, "onDisconnected")
                        onReceiveMessageListener?.onReceiveMessage(Message.DISCONNECTED)
                    }
                }
                server?.start()
                listener.onSuccess("ws:${it.hostAddress}:$port")
                println("ws:${it.hostAddress}:$port")
            }
    }

    override fun release() {
        Log.d(TAG, "release")
        disposable?.dispose()
        server?.stop()
    }
}