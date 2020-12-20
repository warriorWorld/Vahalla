package com.harbinger.valhalla.listener

/**
 * Created by acorn on 2020/12/20.
 */
interface ISender {
    fun setUrl(url: String)
    fun sendMessage(message: String)
}