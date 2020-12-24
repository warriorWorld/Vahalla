package com.harbinger.valhalla

import android.os.Bundle
import android.text.ClipboardManager
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harbinger.valhalla.communication.Customer
import com.harbinger.valhalla.communication.Server
import com.harbinger.valhalla.dialog.*
import com.harbinger.valhalla.listener.ICommunicator
import com.harbinger.valhalla.listener.OnListDialogClickListener
import com.harbinger.valhalla.listener.ServerInitListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var communicator: ICommunicator? = null
    private val clip = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //取消状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
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
            .setEditDialogListener(object : EditDialog.OnEditDialogClickListener {
                override fun onOkClick(result: String?) {
                    communicator = Customer()
                    if (result != null) {
                        (communicator as Customer).connect(result)
                        showToast("已连接")
                        //TODO 通知对方
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
}