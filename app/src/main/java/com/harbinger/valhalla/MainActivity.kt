package com.harbinger.valhalla

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.harbinger.valhalla.communication.Server
import com.harbinger.valhalla.communication.Customer
import com.harbinger.valhalla.dialog.ListDialog
import com.harbinger.valhalla.listener.ICommunicator
import com.harbinger.valhalla.listener.OnListDialogClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var communicator: ICommunicator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //设置状态栏黑色字体
            this.window.statusBarColor = resources.getColor(R.color.white)
        }
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        settings_iv.setOnClickListener {
            showOptionsDialog()
        }
    }

    private fun showOptionsDialog() {
        val options = arrayOf("作为服务器", "作为客户端")
        val dialog = ListDialog(this)
        dialog.show()
        dialog.setOptionsList(options)
        dialog.setOnListDialogClickListener(object : OnListDialogClickListener {
            override fun onItemClick(selectedRes: String?, selectedCodeRes: String?) {
            }

            override fun onItemClick(selectedRes: String?) {
            }

            override fun onItemClick(position: Int) {
                when (position) {
                    0 -> {
                        communicator = Server()

                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        communicator?.release()
    }
}