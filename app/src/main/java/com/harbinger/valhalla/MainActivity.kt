package com.harbinger.valhalla

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.harbinger.valhalla.communication.Receiver
import com.harbinger.valhalla.communication.Sender
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val receiver = Receiver()
    private val sender = Sender()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //设置状态栏黑色字体
            this.window.statusBarColor = resources.getColor(R.color.white)
        }
        setContentView(R.layout.activity_main)
        initCommunication()
        initUI()
    }

    private fun initCommunication() {
        receiver.registReceiver()
        receiver.getMessage().observe(this, Observer {

        })
    }

    private fun initUI() {
        settings_iv.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        receiver.release()
    }
}