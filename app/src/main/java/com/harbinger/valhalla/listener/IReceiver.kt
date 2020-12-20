package com.harbinger.valhalla.listener

import androidx.lifecycle.LiveData

/**
 * Created by acorn on 2020/12/20.
 */
interface IReceiver {
    fun onConnect()
    fun onDisconnect()
    fun onError(msg:String)
    fun registReceiver()
    fun getMessage(): LiveData<String>
    fun release()
}