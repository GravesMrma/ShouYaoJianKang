package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import com.android.mltcode.blecorelib.cmd.Command
import com.android.mltcode.blecorelib.listener.IConnectListener
import com.android.mltcode.blecorelib.listener.WristScannerListener
import com.android.mltcode.blecorelib.manager.BluetoothWristManager
import com.android.mltcode.blecorelib.manager.DataManager
import com.android.mltcode.blecorelib.mode.DeviceStatus
import com.android.mltcode.blecorelib.mode.ScannerStatus
import com.android.mltcode.blecorelib.scanner.ExtendedBluetoothDevice
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.common.BaseConstant
import com.jaeger.library.StatusBarUtil
import com.kotlin.base.utils.AppPrefsUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemFive
import com.wuhanzihai.rbk.ruibeikang.utils.BraceletManagerUtil
import kotlinx.android.synthetic.main.activity_bind_bracelet.*
import kotlinx.android.synthetic.main.item_barcelet.view.*
import org.jetbrains.anko.act
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnyLayer
import java.util.ArrayList

// 手环搜索页面
class BindBraceletActivity : AppCompatActivity() {
    private var adapter: BaseQuickAdapter<Bracelet, BaseViewHolder>? = null
    private val list = ArrayList<Bracelet>()
    private var mac = ""
    private var name = ""
    private lateinit var view: View

    val dialog by lazy {
        val anyLayer = AnyLayer.with(this)
                .contentView(view)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .onClick(R.id.ivClose) { AnyLayer, v ->
                    tvName!!.text = "重新查找设备"
                    tvName!!.isSelected = true
                    tvReScan.visibility = View.VISIBLE
                    BraceletManagerUtil.instance.stopScan()
                    AnyLayer.dismiss()
                }
                .onClick(R.id.tvCollect) { AnyLayer, v ->
                    var isHave = true
                    for (bracelet in list) {
                        if (bracelet.isSelect) {
                            isHave = false
                            bracelet.device!!.connect()
                            mac = bracelet.mac!!
                            name = bracelet.name!!
                            Log.e("TTAAAGGG", "开始连接蓝牙" + bracelet.name!!)
                            tvName!!.text = "正在连接"

                            BraceletManagerUtil.instance.stopScan()
                        }
                    }
                    if (isHave) {
                        toast("您未选中任何设备")
                    } else {
                        AnyLayer.dismiss()
                    }
                    AnyLayer.dismiss()
                }
        anyLayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_bracelet)

        StatusBarUtil.setColor(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    fun initView() {
        view = layoutInflater.inflate(R.layout.item_barcelet, null)
        adapter = object : BaseQuickAdapter<Bracelet, BaseViewHolder>(R.layout.item_bracelet_item, list) {
            override fun convert(helper: BaseViewHolder, item: Bracelet) {
                helper.setText(R.id.tvName, "设备名称: " + item.name!!)
                        .setText(R.id.tvMac, "设备地址: " + item.mac!!)
                helper.getView<View>(R.id.llView).isSelected = item.isSelect
            }
        }
        view.rvView.adapter = adapter
        view.rvView.layoutManager = GridLayoutManager(act, 1)
        view.rvView.addItemDecoration(DividerItemFive(act))
        adapter!!.setOnItemClickListener { _, _, position ->
            for (bracelet in list) {
                bracelet.isSelect = false
            }
            list[position].isSelect = true
            adapter!!.notifyDataSetChanged()
        }

        tvCancel.setOnClickListener {
            finish()
        }

        tvName!!.setOnClickListener {
            if (tvName!!.isSelected) {
                tvName!!.isSelected = false
                tvName!!.text = "查找中..."
            }
        }

        tvReScan.setOnClickListener {
            startScan()
            tvReScan.visibility = View.GONE
        }
    }

    fun initData() {
        BraceletManagerUtil.instance.registerConnect(object : IConnectListener {
            override fun onConnectFailure(p0: String?) {}
            override fun onConectListener(status: DeviceStatus?) {
                if (status == DeviceStatus.CONNECTED) {
                    setText("连接成功")
                } else if (status == DeviceStatus.CONNECTING) {
                    setText("连接中...")
                } else if (status == DeviceStatus.DISCOVERSERVICESING) {
                    setText("正在获取服务...")
                } else if (status == DeviceStatus.DISCONNECT || status == DeviceStatus.NONE) {
                    setText("未连接")
                } else if (status == DeviceStatus.DISCOVERSERVICES_COMPLETED) {
                    setText("获取服务成功")
                    AppPrefsUtils.putString(BaseConstant.BRACELET_MAC, mac)
                    AppPrefsUtils.putString(BaseConstant.BRACELET_NAME, name)

                    //绑定手环  写入数据
                    val command = Command.newInstance()
                    command.data = DataManager.getBindDeviceBytes(AppPrefsUtils.getString(BaseConstant.BRACELET_ID), AppPrefsUtils.getString(BaseConstant.BRACELET_MAC), false)
                    BluetoothWristManager.getInstance().bleDevice.writeData(command)
                    BraceletManagerUtil.instance.getSyncRealDate() // 开启数据同步
                    BraceletManagerUtil.instance.getNote()
                    BraceletManagerUtil.instance.openHeartRateAllDayBytes(30) // 开启全天监测
                    finish()
                }
            }
        })
        startScan()
    }

    private fun startScan() {
        BraceletManagerUtil.instance.startScan(object : WristScannerListener {
            override fun onScanSuccess(extendedBluetoothDevice: ExtendedBluetoothDevice?) {
                val bracelet = Bracelet()
                bracelet.device = extendedBluetoothDevice
                bracelet.name = extendedBluetoothDevice!!.device.name
                bracelet.mac = extendedBluetoothDevice.device.address
                var isHave = false
                for (bracelet1 in list) {
                    if (bracelet1.mac == bracelet.mac) {
                        isHave = true
                    }
                }
                if (!isHave) {
                    list.add(bracelet)
                }
                if (!dialog.isShow) {
                    dialog.show()
                }
            }

            override fun onScanFailure(p0: String?) {}
            override fun onScannerStatus(p0: ScannerStatus?) {}
        })
    }

    private fun setText(text: String) {
        runOnUiThread { tvName!!.text = text }
    }

    override fun onDestroy() {
        super.onDestroy()
        BraceletManagerUtil.instance.stopScan()
    }

    internal inner class Bracelet {
        var device: ExtendedBluetoothDevice? = null
        var mac: String? = null
        var name: String? = null
        var isSelect: Boolean = false
    }
}
