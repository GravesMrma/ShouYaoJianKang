package com.wuhanzihai.rbk.ruibeikang.activity

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.View
import com.android.mltcode.blecorelib.bean.AllDayHeartRateBean
import com.android.mltcode.blecorelib.bean.MsgSwith
import com.android.mltcode.blecorelib.mode.CallbackMode
import com.android.mltcode.blecorelib.mode.MessageMode
import com.android.mltcode.blecorelib.mode.SwithMode
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.bluetooth.NotificationService
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.event.BraceletDataEvent
import com.wuhanzihai.rbk.ruibeikang.utils.BraceletManagerUtil
import kotlinx.android.synthetic.main.activity_bluetooth_menu.*
import kotlinx.android.synthetic.main.base_dialog_loading_text.view.*
import kotlinx.android.synthetic.main.item_my_dialog.view.*
import kotlinx.android.synthetic.main.item_my_dialog.view.tvText
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnyLayer
import pub.devrel.easypermissions.EasyPermissions

class BluetoothMenuActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var view: View
    private lateinit var viewText: View
    private var handler = Handler()


    val dialog by lazy {
        val anyLayer = AnyLayer.with(this)
                .contentView(view)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .onClick(R.id.tvCommit) { AnyLayer, v ->
                    NotificationService.openNotificationAccess(this, NotificationService.SERVICE_RESULT_MSG)
                    AnyLayer.dismiss()
                }
        anyLayer
    }


    val dialogText by lazy {
        val anyLayer = AnyLayer.with(this)
                .contentView(viewText)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .onClick(R.id.tvSearch) { AnyLayer, v ->
                    AnyLayer.dismiss()
                }
        anyLayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_menu)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        view = layoutInflater.inflate(R.layout.item_my_dialog, null)
        viewText = layoutInflater.inflate(R.layout.base_dialog_loading_text, null)

        initView()

        initData()

//         检查是否有通知权限
        if (!isOpenNotify()) {
            handler.postDelayed({
                view.tvText.text = "亲、开启通知权限后、我们就能为您提供手环的来电、短信、微信等提醒功能！"
                dialog.show()
            }, 500)
        } else {
            NotificationService.ensureCollectorRunning(act)
        }
    }

    fun initView() {
        ivCall.setOnClickListener {
            // 先检查是否有通知权限
            // 检查系统权限
            if (isOpenNotify()) {
                val perms = arrayOf(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_CONTACTS
                )
                if (EasyPermissions.hasPermissions(act, *perms)) {
                    for (msgSwith in BraceletData.instance.msgSwitchList!!) {
                        if (msgSwith.type == MessageMode.CALL) {
                            if (ivCall.isSelected) {
                                msgSwith.mode = SwithMode.OFF
                                viewText.tvText.text = "正在关闭..."
                                dialogText.show()
                            } else {
                                msgSwith.mode = SwithMode.ON
                                viewText.tvText.text = "正在开启..."
                                dialogText.show()
                            }
                        }
                    }
                    openNotify()
                } else {
                    EasyPermissions.requestPermissions(this, "你好,APP需要的权限。你能允许吗?", 4444, *perms)
                }
            } else {
                view.tvText.text = "亲、开启通知权限后、我们就能为您提供手环的来电、短信、微信等提醒功能！"
                dialog.show()
            }
        }
        ivMsg.setOnClickListener {
            // 先检查是否有通知权限
            // 检查系统权限
            if (isOpenNotify()) {
                val perms = arrayOf(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.RECEIVE_SMS
                )
                if (EasyPermissions.hasPermissions(act, *perms)) {
                    for (msgSwith in BraceletData.instance.msgSwitchList!!) {
                        if (msgSwith.type == MessageMode.SMSS) {
                            if (ivMsg.isSelected) {
                                msgSwith.mode = SwithMode.OFF
                                viewText.tvText.text = "正在关闭..."
                                dialogText.show()
                            } else {
                                msgSwith.mode = SwithMode.ON
                                viewText.tvText.text = "正在开启..."
                                dialogText.show()
                            }
                        }
                    }
                    openNotify()
                } else {
                    EasyPermissions.requestPermissions(this, "你好,APP需要的权限。你能允许吗?", 4444, *perms)
                }

            } else {
                view.tvText.text = "亲、开启通知权限后、我们就能为您提供手环的来电、短信、微信等提醒功能！"
                dialog.show()
            }
        }
        ivWeChat.setOnClickListener {
            for (msgSwith in BraceletData.instance.msgSwitchList!!) {
                if (msgSwith.type == MessageMode.WETCHAT) {
                    if (ivWeChat.isSelected) {
                        msgSwith.mode = SwithMode.OFF
                        viewText.tvText.text = "正在关闭..."
                        dialogText.show()
                    } else {
                        msgSwith.mode = SwithMode.ON
                        viewText.tvText.text = "正在开启..."
                        dialogText.show()
                    }
                }
            }
            openNotify()
        }
        ivQQ.setOnClickListener {
            for (msgSwith in BraceletData.instance.msgSwitchList!!) {
                if (msgSwith.type == MessageMode.QQ) {
                    if (ivQQ.isSelected) {
                        msgSwith.mode = SwithMode.OFF
                        viewText.tvText.text = "正在关闭..."
                        dialogText.show()
                    } else {
                        msgSwith.mode = SwithMode.ON
                        viewText.tvText.text = "正在开启..."
                        dialogText.show()
                    }
                }
            }
            openNotify()
        }
        ivHealth.setOnClickListener {
            if (ivHealth.isSelected) {
                BraceletManagerUtil.instance.closeHeartRateAllDayBytes()
                viewText.tvSearch.text = "正在关闭..."
                dialogText.show()
            } else {
                BraceletManagerUtil.instance.openHeartRateAllDayBytes(30)
                viewText.tvSearch.text = "正在开启..."
                dialogText.show()
            }
            BraceletManagerUtil.instance.getHeartRateAllDayBytes()
        }
    }

    fun initData() {
        if (BraceletData.instance.msgSwitchList != null) {
            for (msgSwith in (BraceletData.instance.msgSwitchList!!)) {
                if (msgSwith.type == MessageMode.CALL) {
                    ivCall.isSelected = msgSwith.mode == SwithMode.ON
                }
                if (msgSwith.type == MessageMode.SMSS) {
                    ivMsg.isSelected = msgSwith.mode == SwithMode.ON
                }
                if (msgSwith.type == MessageMode.QQ) {
                    ivQQ.isSelected = msgSwith.mode == SwithMode.ON
                }
                if (msgSwith.type == MessageMode.WETCHAT) {
                    ivWeChat.isSelected = msgSwith.mode == SwithMode.ON
                }
            }
        }
        Bus.observe<BraceletDataEvent>().subscribe {
            if (it.callback.mode == CallbackMode.MSG_SWITCH) {
                for (msgSwith in (it.callback.data as List<MsgSwith>)) {
                    if (msgSwith.type == MessageMode.CALL) {
                        runOnUiThread {
                            ivCall.isSelected = msgSwith.mode == SwithMode.ON
                            dialogText.dismiss()
                        }
                    }
                    if (msgSwith.type == MessageMode.SMSS) {
                        runOnUiThread {
                            ivMsg.isSelected = msgSwith.mode == SwithMode.ON
                            dialogText.dismiss()
                        }
                    }
                    if (msgSwith.type == MessageMode.QQ) {
                        runOnUiThread {
                            ivQQ.isSelected = msgSwith.mode == SwithMode.ON
                            dialogText.dismiss()
                        }
                    }
                    if (msgSwith.type == MessageMode.WETCHAT) {
                        runOnUiThread {
                            ivWeChat.isSelected = msgSwith.mode == SwithMode.ON
                            dialogText.dismiss()
                        }
                    }
                }
            }
            if (it.callback.mode == CallbackMode.ALL_DAY_HEART_RATE) {
                runOnUiThread {
                    ivHealth.isSelected = (it.callback.data as AllDayHeartRateBean).mode == SwithMode.ON
                }
            }
        }.registerInBus(this)
        BraceletManagerUtil.instance.getNote()
        BraceletManagerUtil.instance.getHeartRateAllDayBytes()
    }

    private fun openNotify() {
        BraceletManagerUtil.instance.setNote()
        BraceletManagerUtil.instance.getNote()
    }

    private fun isOpenNotify(): Boolean {
        if (NotificationService.isEnabled(this)) {
            NotificationService.ensureCollectorRunning(this)
            return true
        } else {
            return false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 3333) {
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 3333) {
            startActivityForResult<BindBraceletActivity>(7631)
        }
    }
}
