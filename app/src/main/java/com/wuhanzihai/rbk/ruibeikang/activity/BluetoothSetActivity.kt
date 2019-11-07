package com.wuhanzihai.rbk.ruibeikang.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.android.mltcode.blecorelib.manager.BluetoothWristManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.kotlin.base.utils.AppPrefsUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.event.BraceletDataEvent
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemBluetooth
import com.wuhanzihai.rbk.ruibeikang.utils.BraceletManagerUtil
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_bluetooth_set.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnyLayer
import pub.devrel.easypermissions.EasyPermissions

class BluetoothSetActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    val SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010
    var OPEN_BLUETOOTH = 0x01


    val dialog by lazy {
        val anyLayer = AnyLayer.with(this)
                .contentView(R.layout.dialog_unbind)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .onClick(R.id.tvSure) { AnyLayer, v ->
                    BraceletManagerUtil.instance.unBindBlue()
                    BluetoothWristManager.getInstance().bleDevice.disconnect()
                    AnyLayer.dismiss()
                    onResume()
                }
        anyLayer
    }
    private lateinit var list: MutableList<DataItem>
    private lateinit var adapter: BaseQuickAdapter<DataItem, BaseViewHolder>
    private var isResume = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_set)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    fun initView() {
        list = mutableListOf()
        list.add(DataItem("步数", "步", R.drawable.selector_foot_1))
        list.add(DataItem("睡眠", "小时/分钟", R.drawable.selector_foot_2))
        list.add(DataItem("血氧", "HbO2", R.drawable.selector_foot_3))
        list.add(DataItem("心率", "bpm", R.drawable.selector_foot_4))
        list.add(DataItem("问题", "查看", R.mipmap.ic_bre5))
        list.add(DataItem("设置", "去开启", R.drawable.selector_foot_6))

        adapter = object : BaseQuickAdapter<DataItem, BaseViewHolder>(R.layout.item_bluetooth, list) {
            override fun convert(helper: BaseViewHolder?, item: DataItem?) {
                helper!!.setText(R.id.tvTitle, item!!.name)
                        .setImageResource(R.id.ivImg, item.img)
                        .setText(R.id.tvNum, "---")
                        .setText(R.id.tvDw, item.desc)

                helper.getView<TextView>(R.id.tvNum1).visibility = View.GONE
                helper.getView<LinearLayout>(R.id.llView).visibility = View.GONE
                helper.getView<TextView>(R.id.tvNum).visibility = View.VISIBLE
                helper.getView<TextView>(R.id.tvDw).visibility = View.VISIBLE
                helper.getView<TextView>(R.id.tvHour).visibility = View.GONE
                helper.getView<TextView>(R.id.tvMui).visibility = View.GONE
                if (BraceletManagerUtil.instance.getBleDeviceState()) {
                    when (helper.layoutPosition) {
                        0 -> {
                            if (BraceletData.instance.sportsBean != null) {
                                helper.setText(R.id.tvNum, BraceletData.instance.sportsBean!!.step.toString())
                            }
                        }
                        1 -> {
                            helper.getView<LinearLayout>(R.id.llView).visibility = View.VISIBLE
                            helper.getView<TextView>(R.id.tvNum).visibility = View.GONE
                            helper.getView<TextView>(R.id.tvDw).visibility = View.GONE
                            helper.getView<TextView>(R.id.tvHour).visibility = View.VISIBLE
                            helper.getView<TextView>(R.id.tvMui).visibility = View.VISIBLE
                            if (BraceletData.instance.sleepBean != null) {
                                helper.setText(R.id.tvHour, MyUtils.toHour(BraceletData.instance.sleepBean!!.sleepTimeSum))
                                        .setText(R.id.tvMui, MyUtils.toMui(BraceletData.instance.sleepBean!!.sleepTimeSum))
                            }
                        }
                        2 -> {
                            if (BraceletData.instance.bloodOxygenList != null) {
                                helper.setText(R.id.tvNum, BraceletData.instance.bloodOxygenList!![0].value.toString())
                            }
                        }
                        3 -> {
                            if (BraceletData.instance.heartRateBean != null) {
                                helper.setText(R.id.tvNum, BraceletData.instance.heartRateBean!!.heartrate.toString())
                            }
                        }
                        4 -> {

                        }
                        5 -> {
                            helper.getView<TextView>(R.id.tvNum).visibility = View.GONE
                            helper.getView<TextView>(R.id.tvNum1).visibility = View.VISIBLE
                            helper.setText(R.id.tvNum1, R.string.daijsdha)
                        }
                    }
                }
                if(helper.layoutPosition == 4){
                    helper.getView<TextView>(R.id.tvNum).visibility = View.GONE
                    helper.getView<TextView>(R.id.tvNum1).visibility = View.VISIBLE
                    helper.setText(R.id.tvNum1, R.string.jkiu)
                }
                helper.getView<ImageView>(R.id.ivImg).isSelected = BraceletManagerUtil.instance.getBleDeviceState()
                helper.getView<TextView>(R.id.tvTitle).isSelected = BraceletManagerUtil.instance.getBleDeviceState()
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 2)
        rvView.addItemDecoration(DividerItemBluetooth(act))
        adapter.setOnItemClickListener { _, _, position ->
            if (position == 4){
                startActivity<StandardWebActivity>("title" to "常见问题"
                        , "data" to BaseConstant.BASE_URL + "api/Web/article?id=686")
                return@setOnItemClickListener
            }
            if (!BraceletManagerUtil.instance.getBleDeviceState()) {
                toast("对不起，您还未连接手环！")
                return@setOnItemClickListener
            }
            if (position < 5) {
                startActivity<SportDataActivity>(
                        "index" to position
                )
            } else {
                if (position == 5) {
                    startActivity<BluetoothMenuActivity>()
                }
            }
        }

        tvSearch.onClick {
            if (openBluetooth()) {
                toSearch()
            } else {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, OPEN_BLUETOOTH)
            }
        }

        tvUnbind.onClick {
            dialog.show()
        }
    }

    fun initData() {
        Bus.observe<BraceletDataEvent>().subscribe { runOnUiThread {
            if (isResume){
                onResume()
            }
        } }.registerInBus(this)
        reConnect()
    }

    private fun reConnect() {
        if (!BraceletManagerUtil.instance.getBleDeviceState()) {
            if (AppPrefsUtils.getString(BaseConstant.BRACELET_MAC).isNotEmpty()) {
                if (openBluetooth()) {
                    BraceletManagerUtil.instance.reConnectBleDevice()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        runOnUiThread {
            if (BraceletManagerUtil.instance.getBleDeviceState()) {
                lInfo.visibility = View.VISIBLE
                llSearch.visibility = View.GONE
                tvName.text = AppPrefsUtils.getString(BaseConstant.BRACELET_NAME)
                tvMac.text = AppPrefsUtils.getString(BaseConstant.BRACELET_MAC)
            } else {
                lInfo.visibility = View.GONE
                llSearch.visibility = View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }
        isResume = true
    }

    override fun onStop() {
        super.onStop()
        isResume = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isResume = false
    }

    private fun toSearch() {
        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (EasyPermissions.hasPermissions(act, *perms)) {
            startActivityForResult<BindBraceletActivity>(7631)
        } else {
            EasyPermissions.requestPermissions(this, "你好,APP需要的权限。你能允许吗?", 3333, *perms)
        }
    }

    private fun getPer() {
        val perms = arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECEIVE_SMS
        )
        if (!EasyPermissions.hasPermissions(act, *perms)) {
            EasyPermissions.requestPermissions(this, "你好,APP需要的权限。你能允许吗?", 4444, *perms)
        }
    }

    private fun openBluetooth(): Boolean {
        // 检查设备是否支持蓝牙
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (adapter != null) {
            return adapter.isEnabled
        } else {
            toast("设备不支持蓝牙")
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OPEN_BLUETOOTH) {
            if (openBluetooth()) {
                toSearch()
            } else {
                toast("设备未开启蓝牙")
            }
        }
        if (requestCode == 7631) {
            if (BraceletManagerUtil.instance.getBleDeviceState()) {
                getPer()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 3333) {
            startActivityForResult<BindBraceletActivity>(7631)
        }
    }

    inner class DataItem {
        var name: String = ""
        var desc: String = ""
        var img: Int = 0

        constructor(name: String, desc: String, img: Int) {
            this.name = name
            this.desc = desc
            this.img = img
        }
    }
}
