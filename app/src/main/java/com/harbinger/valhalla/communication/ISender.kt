package com.harbinger.valhalla.communication

/**
 * Created by acorn on 2020/12/20.
 */
interface ISender : ICommunicator {
    fun connect(url: String)
}