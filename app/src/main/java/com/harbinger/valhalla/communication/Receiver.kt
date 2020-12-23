package com.harbinger.valhalla.communication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harbinger.valhalla.listener.IReceiver
import com.harbinger.valhalla.listener.MessageListener
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
class Receiver : IReceiver {
    private var disposable: Disposable? = null
    private var server: MyWebSocketServer? = null
    private var address = MutableLiveData<String>()
    private var message = MutableLiveData<String>()

    override fun onConnect() {
    }

    override fun onDisconnect() {
    }

    override fun onError(msg: String) {
    }

    override fun registReceiver() {
        val port = 43496
        disposable = Observable.create<InetAddress> {
            it.onNext(NetUtils.getLocalIPAddress())
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                server = MyWebSocketServer(InetSocketAddress(it, port))
                server?.messageListener = object : MessageListener {
                    override fun onMessage(msg: String?) {
                        message.value = msg
                    }
                }
                server?.start()
                address.value = "ws:${it.hostAddress}:$port"
                println("ws:${it.hostAddress}:$port")
            }
    }

    override fun getMessage(): LiveData<String> {
        return message
    }

    override fun getAddress(): LiveData<String> {
        return address
    }

    override fun release() {
        disposable?.dispose()
    }
}