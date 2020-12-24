package com.harbinger.valhalla.listener

import androidx.lifecycle.LiveData

/**
 * Created by acorn on 2020/12/24.
 */
interface ICommunicator {
    fun sendMessage(message: String)
    fun getMessage(): LiveData<String>
    fun release()
}