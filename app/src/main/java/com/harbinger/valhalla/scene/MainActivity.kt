package com.harbinger.valhalla.scene

import android.os.Bundle
import android.text.ClipboardManager
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.harbinger.valhalla.R
import com.harbinger.valhalla.adapter.GodsAdapter
import com.harbinger.valhalla.adapter.HpAdapter
import com.harbinger.valhalla.bean.GodBean
import com.harbinger.valhalla.bean.MessageBean
import com.harbinger.valhalla.communication.*
import com.harbinger.valhalla.dialog.*
import com.harbinger.valhalla.gameplay.GameState
import com.harbinger.valhalla.listener.OnListDialogClickListener
import com.harbinger.valhalla.listener.ServerInitListener
import com.harbinger.valhalla.maker.GodMaker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), IValhallaScene {
    private var communicator: ICommunicator? = null
    private lateinit var clip: ClipboardManager
    private val isMyRound = MutableLiveData<Boolean>()
    private val currentState = MutableLiveData<GameState>()
    private val godsList = ArrayList<GodBean>()
    private val enemyGodsList = ArrayList<GodBean>()
    private val hpAdapter = HpAdapter(this)
    private val godsAdapter = GodsAdapter(this)
    private val enemyHpAdapter = HpAdapter(this)
    private val enemyGodsAdapter = GodsAdapter(this)

    private val onReceiveMessageListener = object : OnReceiveMessageListener {
        override fun onReceiveMessage(message: String, details: String?) {
            when (message) {
                Message.CONNECTED -> {
                    showConnected()
                }
                Message.DISCONNECTED -> {
                    showDisconnected()
                }
                Message.START -> {
                    startGame()
                }
                Message.ROUND_AFFILIATION -> {
                    isMyRound.value = details?.equals("true")
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
        initMyGodsList()
        observeGameState()
        initUI()
    }

    private fun observeGameState() {
        isMyRound.observe(this, androidx.lifecycle.Observer {
            if (it) {
                tip_tv.text = "你的回合"
                dice_iv.visibility = View.VISIBLE
                end_iv.visibility = View.VISIBLE
            } else {
                tip_tv.text = "对方回合"
                dice_iv.visibility = View.GONE
                end_iv.visibility = View.GONE
            }
        })
        currentState.observe(this, androidx.lifecycle.Observer {
            when (it) {
                GameState.DICE1 -> {

                }
                GameState.DICE2 -> {

                }
                GameState.DICE3 -> {

                }
                GameState.CHOOSE1 -> {

                }
                GameState.CHOOSE2 -> {

                }
                GameState.BLISS -> {

                }
                GameState.FIGHT -> {

                }
            }
        })
    }

    private fun initMyGodsList() {
        godsList.add(GodMaker.getLifeGod())
        godsList.add(GodMaker.getHelmetHater())
        //TODO 通过commicator获取
        enemyGodsList.add(GodMaker.getLifeGod())
        enemyGodsList.add(GodMaker.getHelmetHater())
    }

    private fun initUI() {
        hp_rcv.layoutManager = GridLayoutManager(this, 5)
        hp_rcv.isFocusableInTouchMode = false
        hp_rcv.isFocusable = false
        hp_rcv.setHasFixedSize(true)
        hp_rcv.adapter = hpAdapter
        enemy_hp_rcv.layoutManager = GridLayoutManager(this, 5)
        enemy_hp_rcv.isFocusableInTouchMode = false
        enemy_hp_rcv.isFocusable = false
        enemy_hp_rcv.setHasFixedSize(true)
        enemy_hp_rcv.adapter = enemyHpAdapter
        enemyHpAdapter.setImageRes(R.drawable.ic_heart2)
        gods_rcv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        gods_rcv.isFocusableInTouchMode = false
        gods_rcv.isFocusable = false
        gods_rcv.setHasFixedSize(true)
        gods_rcv.adapter = godsAdapter
        enemy_gods_rcv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        enemy_gods_rcv.isFocusableInTouchMode = false
        enemy_gods_rcv.isFocusable = false
        enemy_gods_rcv.setHasFixedSize(true)
        enemy_gods_rcv.adapter = enemyGodsAdapter

        settings_iv.setOnClickListener {
            showOptionsDialog()
        }
        start_iv.setOnClickListener {
            communicator?.sendMessage(Message.START)
            startGame()
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
                        //固定由服务端摇骰决定谁先
                        val random = Random()
                        isMyRound.value = random.nextBoolean()

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
                        showToast("已连接至$result")
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
        runOnUiThread { start_iv.setImageResource(R.drawable.ic_start) }
    }

    override fun showDisconnected() {
        runOnUiThread {
            control_group.visibility = View.VISIBLE
            game_group.visibility = View.GONE
            start_iv.setImageResource(R.drawable.ic_start_gray)
        }
    }

    override fun startGame() {
        if (null != isMyRound.value) {
            communicator?.sendMessage(
                MessageBean(
                    Message.ROUND_AFFILIATION,
                    isMyRound.value.toString()
                )
            )
        }
        runOnUiThread {
            control_group.visibility = View.GONE
            game_group.visibility = View.VISIBLE
            hpAdapter.setRemainHp(15)
            enemyHpAdapter.setRemainHp(15)
            godsAdapter.setGods(godsList)
            godsAdapter.notifyDataSetChanged()
            enemyGodsAdapter.setGods(enemyGodsList)
            enemyGodsAdapter.notifyDataSetChanged()
        }
    }
}