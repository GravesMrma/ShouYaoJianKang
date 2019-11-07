package com.wuhanzihai.rbk.ruibeikang.activity

import android.Manifest
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_blood_check.*
import org.jetbrains.anko.act
import per.goweii.anylayer.AnyLayer
import java.util.*
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGatt
import android.content.Intent
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.dialog_bluetooth.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.EasyPermissions

// 血压 蓝牙 设备测量页面
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class BloodCheckActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    val dialog by lazy {
        val anyLayer = AnyLayer.with(this)
                .contentView(view!!)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .onClick(R.id.tvConnect) { AnyLayer, v ->
                    if (!isConnect) {
                        view!!.tvConnect!!.text = "正在连接"
                        mBluetoothGatt = bluetoothDevice!!.connectGatt(act, false, mGattCallback)
                        isConnect = true
                    }
                }
                .onClick(R.id.tvClose) { AnyLayer, v ->
                    stopScan()
                    AnyLayer.dismiss()
                }
                .onClick(R.id.tvReconnect) { AnyLayer, v ->
                    view!!.tvBluetoothName.text = "查找中..."
                    view!!.lView.visibility = View.INVISIBLE
                    startScan()
                }.onLayerDismissListener(object : AnyLayer.OnLayerDismissListener {
                    override fun onDismissing(anyLayer: AnyLayer?) {}
                    override fun onDismissed(anyLayer: AnyLayer?) {
                        handler.removeCallbacks(run)
                    }
                })
        anyLayer
    }

    private val mBluetoothAdapter: BluetoothAdapter by lazy {
        val mBluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothManager.adapter
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

    private var bluetoothDevice: BluetoothDevice? = null
    private var mCharacteristic: BluetoothGattCharacteristic? = null
    private var mBluetoothGatt: BluetoothGatt? = null
    private var type = 0
    private var view: View? = null

    private var isConnect = false
    private var isTimeout = true
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_check)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        tvTitle.setTitleText(intent.getStringExtra("title"))
        type = intent.getIntExtra("type", -1)
        if (type == 1){
            ivImg.setImageResource(R.mipmap.xueya)
        }
        if (type == 2){
            ivImg.setImageResource(R.mipmap.xuetang)
        }

        view = layoutInflater.inflate(R.layout.dialog_bluetooth, null)

        initView()

        initData()
    }

    private fun initView() {
        tvBuy.onClick {
            if (type == 1){
                startActivity<GoodsDetailActivity>("id" to 57)
            }
            if (type == 2){
                startActivity<GoodsDetailActivity>("id" to 58)
            }
        }
        tvConnect1.onClick {
            // 检查是否开启蓝牙
            if (openBluetooth()) {
                startSearch()
            } else {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, OPEN_BLUETOOTH)
            }
        }
    }

    private fun initData() {

    }

    private fun startSearch() {
        // 蓝牙要用的地理  "android.permission.ACCESS_FINE_LOCATION"
        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (EasyPermissions.hasPermissions(act, *perms)) {
            view!!.tvBluetoothName.text = "查找中..."
            view!!.tvConnect.visibility = View.INVISIBLE
            view!!.lView.visibility = View.GONE
            dialog.show()
            startScan()
        } else {
            EasyPermissions.requestPermissions(this, "你好,APP需要的权限。你能允许吗?", 3333, *perms)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == OPEN_BLUETOOTH) {
            if (openBluetooth()) {
                startSearch()
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
            startSearch()
        }
    }

    private fun startScan() {
        isTimeout = true
        mBluetoothAdapter.bluetoothLeScanner.startScan(object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                if (result!!.device.name != null) {
                    if (result.device.name == BaseConstant.BP_DEVICE_NAME) {
                        // 血压器材
                        view!!.tvName!!.text = "智能蓝牙血压计"
                        view!!.tvBluetoothName!!.text = result.device.name
                        view!!.tvConnect!!.visibility = View.VISIBLE
                        bluetoothDevice = result.device
                        view!!.lView.visibility = View.GONE
                        isTimeout = false
                    }
                    if (result.device.name == BaseConstant.GLU_DEVICE_NAME) {
                        // 血糖器材
                        view!!.tvName!!.text = "智能蓝牙血糖仪"
                        view!!.tvBluetoothName!!.text = result.device.name
                        view!!.tvConnect!!.visibility = View.VISIBLE
                        bluetoothDevice = result.device
                        view!!.lView.visibility = View.GONE
                        isTimeout = false
                    }
                }
            }
        })
        handler.postDelayed(run, 15000)
    }

    private fun stopScan() {
        mBluetoothAdapter.bluetoothLeScanner.stopScan(object : ScanCallback() {})
    }

    private var run = Runnable {
        if (isTimeout) {
            isConnect = false
            view!!.tvConnect.visibility = View.GONE
            view!!.lView.visibility = View.VISIBLE
            view!!.tvBluetoothName.text = "查找超时..."
        }
    }

    /**
     * 蓝牙链接回调
     */
    private val mGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            if (BluetoothGatt.STATE_CONNECTED == newState) {
                Log.e("蓝牙连接——状态", "连接成功:")
                gatt.discoverServices();//必须有，可以让onServicesDiscovered显示所有Services
                runOnUiThread {
                    view!!.tvBluetoothName!!.isSelected = true
                    view!!.tvBluetoothName!!.text = "连接成功"
                    view!!.tvConnect!!.text = "等待测量"
                }
            } else if (BluetoothGatt.STATE_DISCONNECTED == newState) {
                Log.e("蓝牙连接——状态", "断开连接:");
                runOnUiThread {
                    view!!.tvBluetoothName!!.isSelected = true
                    view!!.tvBluetoothName!!.text = "断开连接"
                    view!!.tvConnect!!.text = "连接"
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            Log.e("蓝牙连接——状态", "onServicesDiscovered")
            if (BluetoothGatt.GATT_SUCCESS == status) {
                displayGattServices(gatt.services)
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            if (characteristic == null) {
                Log.e("蓝牙连接——数据", "characteristic == null")

                return
            }
            Log.e("蓝牙连接——数据", Arrays.toString(characteristic.value) + "")
            if (type == 1) {
                if (characteristic.value.size >= 10) {
                    var data = characteristic.value
                    var zhi = "${data[6]}/${data[8]}"
                    startActivity<BloodPreDataDetailActivity>("data" to zhi
                            , "type" to type
                            , "rate" to data[9].toInt()
                            , "state" to "设备测量")
                    gatt.disconnect()
                    gatt.close()
                    finish()
                }
            } else if (type == 2) {
                if (characteristic.value.size >= 15) {
                    var data = characteristic.value
                    var zhi = data[13]
                    var type1 = ""
                    var time = -1
                    if (data[0].toInt() > 0) {
                        type1 = "餐前"
                        time = 0
                    } else {
                        type1 = "餐后"
                        time = 1
                    }
                    startActivity<BloodPreDataDetailActivity>("data" to (zhi.toString().toDouble() / 10).toString()
                            , "type" to type
                            , "time" to time
                            , "state" to "设备测量"
                            , "typetext" to type1)
                    gatt.disconnect()
                    gatt.close()
                    finish()
                }
            }


//            Log.e("蓝牙连接——数据", characteristic.describeContents().toString() + "")
//            Log.e("蓝牙连接——数据", characteristic.describeContents().toString() + "")

//            Log.d("返回的数据" + "高压：", date[6] + "");
//            Log.d("返回的数据" + "低压：", date[8] + "");
//            Log.d("返回的数据" + "脉搏：", date[9] + "");

            //  血压  [-1, -2, 8, 104, 85, 0, 119, 0, 81, 67]
            //  血糖   [71, 0, 0, -32, 7, 5, 8, 1, 16, 0, 0, 0, -16, 29, 17]   拿到13位的数据 转成10进制 除以10 就是血糖的数据
//            measuringModel.setMeasuringData(BluetoothValueUtil.getInstance().bytesToDemicals(characteristic.value))
//            mMeasuringListener.onCharacteristicChanged(measuringModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (mBluetoothGatt!=null){
//            mBluetoothGatt!!.close()
//        }
    }

    /**
     * 获取特征值（特征UUID） 用来做与蓝牙通讯的唯一通道
     *
     * @param gattServices BluetoothGattService
     */
    private fun displayGattServices(gattServices: List<BluetoothGattService>?) {
        if (gattServices == null) {
            Log.e("", "BluetoothGattService list is null")
            return
        }
        for (gattService in gattServices) {
            val gattCharacteristics = gattService.characteristics
            for (characteristic in gattCharacteristics) {
                val property = characteristic.properties
                if (property and BluetoothGattCharacteristic.PROPERTY_WRITE > 0 || property and BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE > 0) {
                    this.mCharacteristic = characteristic
                    notification(characteristic)
                }
                if (property and BluetoothGattCharacteristic.PROPERTY_READ > 0) {
                    notification(characteristic)
                }
                if (property and BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0) {
                    notification(characteristic)
                }
            }
        }
    }

    private fun notification(gattCharacteristic: BluetoothGattCharacteristic) {
        val success = mBluetoothGatt!!.setCharacteristicNotification(gattCharacteristic, true)
        if (!success) {
            return
        }
        for (dp in gattCharacteristic.descriptors) {
            if (dp == null) {
                continue
            }
            if (gattCharacteristic.properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
                dp.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            }
            mBluetoothGatt!!.writeDescriptor(dp)
            try {
                Thread.sleep(200)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}
