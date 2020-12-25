package com.harbinger.valhalla.scene

import android.os.Bundle
import android.text.ClipboardManager
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harbinger.valhalla.R
import com.harbinger.valhalla.communication.*
import com.harbinger.valhalla.dialog.*
import com.harbinger.valhalla.listener.OnListDialogClickListener
import com.harbinger.valhalla.listener.ServerInitListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IValhallaScene {
    private var communicator: ICommunicator? = null
    private lateinit var clip: ClipboardManager
    private var isMyRound = false
    private val onReceiveMessageListener = object : OnReceiveMessageListener {
        override fun onReceiveMessage(message: String, details: String?) {
            when (message) {
                Message.CONNECTED -> {
                    showConnected()
                }
                Message.DISCONNECTED -> {
                    showDisconnected()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //取消状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        clip = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        initUI()
    }

    private fun initUI() {
        settings_iv.setOnClickListener {
            showOptionsDialog()
        }
        start_iv.setOnClickListener {
            communicator?.sendMessage(Message.CONNECTED)
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
                        communicator?.setOnReceiveMessageListener(onReceiveMessageListener)
                        (communicator as Server).initReceiver(object : ServerInitListener {
                            override fun onSuccess(address: String) {
                                showOpenServerDialog(address)
                            }
                        })
                    }
                    1 -> {
                        showConnectDialog()
                    }
                }
            }
        })
    }

    private fun showOpenServerDialog(address: String) {
        NormalDialogBuilder(this)
            .setTitle("已创建服务")
            .setTitleBold(true)
            .setTitleLeft(true)
            .setMessage("地址:$address")
            .setOkText("点击复制")
            .setOnDialogClickListener(object : NormalDialog.OnDialogClickListener {
                override fun onOkClick() {
                    clip.text = address
                    showToast("已复制")
                }

                override fun onCancelClick() {
                }
            })
            .create()
            .show()
    }

    private fun showConnectDialog() {
        EditDialogBuilder(this)
            .setTitle("连接至服务")
            .setHint("请输入服务器地址")
            .setTitleBold(true)
            .setTitleLeft(true)
            .setOkText("连接")
            .setCancelText("取消")
            .setEditDialogListener(object : EditDialog.OnEditDialogClickListener {
                override fun onOkClick(result: String?) {
                    communicator = Customer()
                    communicator?.setOnReceiveMessageListener(onReceiveMessageListener)
                    if (result != null) {
                        (communicator as Customer).connect(result)
                        showToast("已连接")
                        showConnected()
                        communicator?.sendMessage(Message.CONNECTED)
                    } else {
                        showToast("请输入服务器地址")
                    }
                }

                override fun onCancelClick() {
                }
            })
            .create()
            .show()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        communicator?.release()
    }

    override fun showConnected() {
        game_bg.setBackgroundResource(R.color.colorPrimary)
    }

    override fun showDisconnected() {
        game_bg.setBackgroundResource(R.color.white)
    }
}