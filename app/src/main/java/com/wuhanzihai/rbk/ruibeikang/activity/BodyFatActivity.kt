package com.wuhanzihai.rbk.ruibeikang.activity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.vtrump.vtble.VTDevice
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_body_fat.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import com.vtrump.vtble.VTDeviceManager
import com.vtrump.vtble.VTDeviceScale
import org.jetbrains.anko.act
import per.goweii.anylayer.AnyLayer
import com.vtrump.vtble.VTModelIdentifier
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BodyFatData
import org.json.JSONException
import org.json.JSONObject
import pub.devrel.easypermissions.EasyPermissions
import java.text.SimpleDateFormat
import java.util.ArrayList

class BodyFatActivity : AppCompatActivity(), VTDeviceManager.VTDeviceManagerListener, EasyPermissions.PermissionCallbacks {

    val dialog by lazy {
        val anyLayer = AnyLayer.with(this)
                .contentView(R.layout.dialog_bluetooth)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .onClick(R.id.tvClose) { AnyLayer, v ->
                    AnyLayer.dismiss()
                }
        anyLayer
    }

    private lateinit var manager: VTDeviceManager
    private lateinit var mDevice: VTDeviceScale
    private var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_fat)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

    }

    private fun initView() {
        manager = VTDeviceManager.getInstance()
        manager.key = "C0ZAZTACQ08MPSGH"
        manager.setDeviceManagerListener(this)
        manager.startBle(act)
        dialog.getView<TextView>(R.id.tvName).text = "体脂称"


        dialog.getView<TextView>(R.id.tvDesc).visibility = View.VISIBLE
        tvStart.onClick {
            initData()
        }
        tvBuy.onClick {
            startActivity<GoodsDetailActivity>("id" to 56)
        }
        tvData.onClick {
            if (result.isNotEmpty()) {
                startActivity<BodyFatDataActivity>("data" to result)
            }else{
                showTextDesc(act,"请完成测量后再查询结果")
            }
        }
    }

    private fun initData() {
        // 检查是否开启蓝牙
        if (openBluetooth()) {
            startBlue()
        } else {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, OPEN_BLUETOOTH)
        }
    }

    private fun startBlue() {
        // 蓝牙要用的地理  "android.permission.ACCESS_FINE_LOCATION"
        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(act, *perms)) {
            dialog.show()
            // 设置要扫描的设备列表，具体参数请联系相关人员
            var list = mutableListOf<VTModelIdentifier>()
            var a1 = "03"
            var a2 = "03"
            var a3 = "06"
            list.add(VTModelIdentifier(a1.toByte(), a2.toByte(), a3.toByte(), 0f.toByte()))
            manager.startScan(20, list as ArrayList<VTModelIdentifier>?)
        } else {
            EasyPermissions.requestPermissions(this, "你好,APP需要的权限。你能允许吗?", 3333, *perms)
        }
    }

    private fun getAge(): Int {
        return SimpleDateFormat("yyyy").format(System.currentTimeMillis()).toInt() - GlobalBaseInfo.getBaseInfo()!!.birthday.substring(0, 4).toInt()
    }

    private val listener = object : VTDeviceScale.VTDeviceScaleListener() {
        override fun onDataAvailable(res: String?) {
            Log.e("收到体重数据", res!!.toString())
            var info = JSONObject()
            var jsonObject = JSONObject(res)
            if (jsonObject.getInt("code") == 200) {
                info.put("height", GlobalBaseInfo.getBaseInfo()!!.height)
                info.put("age", getAge())
                info.put("gender", GlobalBaseInfo.getBaseInfo()!!.sex - 1)
                mDevice.setmUserInfo(info)
            } else if (jsonObject.getInt("code") == 0) {
                Log.e("收到体脂数据", res.toString())
                val gson = Gson()
                var bodyFatData = gson.fromJson(res, BodyFatData::class.java)
                result = res
                dialog.dismiss()
                if (bodyFatData.code == 0) {
                    tvBMI.text = bodyFatData.details.bmi.toString()
                    tvWeight.text = bodyFatData.details.weight.toString().substring(0,
                            bodyFatData.details.weight.toString().indexOf("."))
                    tvDouble.text = bodyFatData.details.weight.toString().substring(bodyFatData.details.weight.toString().indexOf(".")+1)
                    tvBodyfat.text = bodyFatData.details.ratioOfFat.toString()
                }
            } else {
                Log.e("收到体脂数据", res.toString())
                val gson = Gson()
                var bodyFatData = gson.fromJson(res, BodyFatData::class.java)
                result = res
                dialog.dismiss()
                tvWeight.text = bodyFatData.details.weight.toString().substring(0,
                        bodyFatData.details.weight.toString().indexOf("."))
                tvDouble.text = bodyFatData.details.weight.toString().substring(bodyFatData.details.weight.toString().indexOf(".")+1)
            }
        }

        override fun onRssiReceived(i: Int) {}
    }

    override fun onDeviceDisconnected(p0: VTDevice?) {
        Log.e("bodyfat", "onDeviceDisconnected")
    }

    override fun onDevicePaired(p0: VTDevice?) {
        Log.e("bodyfat", "onDevicePaired")
    }

    override fun onScanStop() {
        Log.e("bodyfat", "onScanStop")
    }

    override fun onDeviceAdvDisappeared(p0: VTDevice?) {
        Log.e("bodyfat", "onDeviceAdvDisappeared")
    }

    override fun onInited() {
        Log.e("bodyfat", "onInited")
    }

    override fun onDeviceConnected(p0: VTDevice?) {
        Log.e("bodyfat", "onDeviceConnected")
    }

    override fun onDeviceServiceDiscovered(p0: VTDevice?) {
        Log.e("bodyfat", "onDeviceServiceDiscovered")
    }

    override fun onDeviceDiscovered(p0: VTDevice?) {
        Log.e("bodyfat", "onDeviceDiscovered")
    }

    override fun onDeviceAdvDiscovered(p0: VTDevice?) {
        Log.e("bodyfat", "onDeviceAdvDiscovered")
        mDevice = p0 as VTDeviceScale
        mDevice.setScaleDataListener(listener)
        dialog.getView<TextView>(R.id.tvBluetoothName).text = "已连接"
        dialog.getView<TextView>(R.id.tvBluetoothName).isSelected = true
        dialog.getView<TextView>(R.id.tvConnect).visibility = View.VISIBLE
        dialog.getView<TextView>(R.id.tvConnect).text = "等待测量"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OPEN_BLUETOOTH) {
            if (openBluetooth()) {
                startBlue()
            } else {
                toast("设备未开启蓝牙")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.e("权限问题", "onPermissionsGranted")
        if (requestCode == 3333) {
            startBlue()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    // 检查设备是否开启蓝牙
    private var OPEN_BLUETOOTH = 0x01

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
}
