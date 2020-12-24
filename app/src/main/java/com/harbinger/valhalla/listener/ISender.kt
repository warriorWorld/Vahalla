package com.harbinger.valhalla.listener

import androidx.lifecycle.LiveData

/**
 * Created by acorn on 2020/12/20.
 */
interface ISender :ICommunicator{
    fun connect(url: String)
}