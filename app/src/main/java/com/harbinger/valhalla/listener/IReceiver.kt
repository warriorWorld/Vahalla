package com.harbinger.valhalla.listener

import androidx.lifecycle.LiveData

/**
 * Created by acorn on 2020/12/20.
 */
interface IReceiver : ICommunicator {
    fun initReceiver()
    fun getAddress(): LiveData<String>
}