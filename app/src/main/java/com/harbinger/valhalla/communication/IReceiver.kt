package com.harbinger.valhalla.communication

import com.harbinger.valhalla.listener.ServerInitListener

/**
 * Created by acorn on 2020/12/20.
 */
interface IReceiver : ICommunicator {
    fun initReceiver(listener: ServerInitListener)
}