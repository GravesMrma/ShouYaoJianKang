package com.wuhanzihai.rbk.ruibeikang.utils

import android.util.Log
import com.android.mltcode.blecorelib.cmd.Command
import com.android.mltcode.blecorelib.listener.BleDataListener
import com.android.mltcode.blecorelib.listener.IConnectListener
import com.android.mltcode.blecorelib.listener.InitializeListener
import com.android.mltcode.blecorelib.listener.WristScannerListener
import com.android.mltcode.blecorelib.manager.BluetoothWristManager
import com.android.mltcode.blecorelib.manager.DataManager
import com.android.mltcode.blecorelib.mode.DeviceStatus
import com.android.mltcode.blecorelib.mode.SwithMode
import com.android.mltcode.blecorelib.mode.SyncDataMode
import com.android.mltcode.blecorelib.mode.SyncDataType
import com.android.mltcode.blecorelib.scanner.BluetoothDeviceUUIDFilter
import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData

class BraceletManagerUtil {
    companion object {
        val instance by lazy { BraceletManagerUtil() }
    }

    interface ConnectListener {
        fun onConnect()
    }

    // 初始化 -- 初始化成功 -- 注册数据监听
    fun initBracelet(listener: InitializeListener) {
        BluetoothWristManager.getInstance().initialize(BaseApplication.context, "bym", "bym", listener)
    }

    // 判断是否连接上了
    fun getBleDeviceState(): Boolean {
        return BluetoothWristManager.getInstance().bleDevice.status == DeviceStatus.DISCOVERSERVICES_COMPLETED
    }


    //  注册连接监听
    fun registerConnect(listener: IConnectListener) {
        BluetoothWristManager.getInstance().bleDevice.registerConnectListener(listener)
    }

    //开始扫描
    fun startScan(listener: WristScannerListener) {
        BluetoothWristManager.getInstance().startWristScanner(BluetoothDeviceUUIDFilter(), listener)
    }

    // 停止扫描
    fun stopScan() {
        BluetoothWristManager.getInstance().stopWristScanner()
    }

    // 设置自动连接
    fun setAutoConnect() {
        BluetoothWristManager.getInstance().bleDevice.setAutoConnect(true, 10) { b, i -> }
    }

    // 解绑手环
    fun unBindBlue() {
        val command = Command.newInstance()
        command.data = DataManager.getUnBindDeviceBytes(false, true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }

    // 重新连接
    fun reConnectBleDevice(listener: ConnectListener) {
        if (!getBleDeviceState()) {
            Log.e("Callback", "注册连接监听.....")
            registerConnect(object : IConnectListener {
                override fun onConnectFailure(s: String) {}
                override fun onConectListener(deviceStatus: DeviceStatus) {
                    Log.e("Callback", "重连接成功")
                    if (deviceStatus == DeviceStatus.DISCOVERSERVICES_COMPLETED) {
                        Log.e("Callback", "重连接成功——获取服务成功")
                        val command = Command.newInstance()
                        command.data = DataManager.getBindDeviceBytes(AppPrefsUtils.getString(BaseConstant.BRACELET_ID), AppPrefsUtils.getString(BaseConstant.BRACELET_MAC), false)
                        BluetoothWristManager.getInstance().bleDevice.writeData(command)
                        Log.e("Callback", "写入数据成功-绑定成功" + AppPrefsUtils.getString(BaseConstant.BRACELET_ID) + AppPrefsUtils.getString(BaseConstant.BRACELET_MAC))

                        listener.onConnect()
                        getSyncRealDate() // 开启数据同步
                        getNote()
                        openHeartRateAllDayBytes(30) // 开启全天监测

                        Log.e("Callback", "开启数据同步")
                    }
                }
            })
            Log.e("Callback", "重连接中.....${AppPrefsUtils.getString(BaseConstant.BRACELET_MAC)}")
            BluetoothWristManager.getInstance().bleDevice.connect(AppPrefsUtils.getString(BaseConstant.BRACELET_MAC))
        }else{
            Log.e("Callback", "已连接.....")
        }
    }

    // 注册数据监听
    fun registerDataListener(listener: BleDataListener) {
        BluetoothWristManager.getInstance().bleDevice.registerDataListenr(listener)
    }

    // 获取所有提醒
    fun getNote() {
        val command = Command.newInstance()
        command.data = DataManager.getMsgSwitchStateBytes(true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }


    // 设置提醒
    fun setNote() {
        val command = Command.newInstance()
        command.data = DataManager.getMessagePushSwitchBytes(BraceletData.instance.msgSwitchList, true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
            Log.e("Callback", "写入提醒成功")
        }
    }


    // 获取步数 睡眠 电量 组合数据
    fun getCombinationData() {
        val command = Command.newInstance()
        command.data = DataManager.getCombinationDataBytes(SyncDataType.SPORT or SyncDataType.BATTERY or SyncDataType.SLEEP, true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }

    // 开启全天心率检测
    fun openHeartRateAllDayBytes(i: Int) {
        val command = Command.newInstance()
        command.data = DataManager.getHeartRateAllDayBytes(SwithMode.ON, i, true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }

    // 关闭全天心率检测
    fun closeHeartRateAllDayBytes() {
        val command = Command.newInstance()
        command.data = DataManager.getHeartRateAllDayBytes(SwithMode.OFF, 1, true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }

    // 获取全天检测的状态
    fun getHeartRateAllDayBytes() {
        val command = Command.newInstance()
        command.data = DataManager.getReadHeartRateAllDayBytes(true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }

    // 废物接口
    fun getHeartRate() {
        val command = Command.newInstance()
        command.data = DataManager.getHeartrateBytes(true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }

    // 开始数据同步
    fun getSyncRealDate() {
        val command = Command.newInstance()
        command.data = DataManager.getSyncRealdateBytes(SyncDataMode.ALL, true)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }

    // 关闭数据同步
    fun closeSyncRealDate() {
        val command = Command.newInstance()
        command.data = DataManager.getSyncRealdateBytes(SyncDataMode.CLOSE, false)
        if (command.data != null && BluetoothWristManager.getInstance().bleDevice != null) {
            BluetoothWristManager.getInstance().bleDevice.writeData(command)
        }
    }

}