package com.harbinger.valhalla.scene

/**
 * Created by acorn on 2020/12/25.
 */
interface IValhallaScene {
    fun showConnected()
    fun showDisconnected()
    fun startGame()
    fun setOnEndClick(method: () -> Unit)
}