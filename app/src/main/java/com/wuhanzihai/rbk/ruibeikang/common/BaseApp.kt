package com.wuhanzihai.rbk.ruibeikang.common

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.android.mltcode.blecorelib.bean.*
import com.android.mltcode.blecorelib.listener.BleDataListener
import com.android.mltcode.blecorelib.listener.InitializeListener
import com.android.mltcode.blecorelib.manager.Callback
import com.android.mltcode.blecorelib.mode.CallbackMode
import com.eightbitlab.rxbus.Bus
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.hhjt.baselibrary.common.BaseApplication
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.BitmapMemoryCacheParamsSupplier
import com.hhjt.baselibrary.utils.DateUtils
import com.huawei.android.hms.agent.HMSAgent
import com.huawei.hms.support.log.HMSDebugger
import com.kotlin.base.utils.AppPrefsUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.event.BraceletDataEvent
import com.wuhanzihai.rbk.ruibeikang.utils.BraceletManagerUtil
import java.io.File

class BaseApp : BaseApplication() {


    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(context)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableAutoLoadMore(false)
            ClassicsFooter(context)
        }
    }

    override fun onCreate() {
        super.onCreate()
        HMSAgent.init(this)
        initFresco()
        ARouter.init(this)
    }

    private fun initFresco() {
        val diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(File("${filesDir.path}/caches"))
                .setBaseDirectoryName("rsSystemPicCache").setMaxCacheSize((200 * ByteConstants.MB).toLong())
                .setMaxCacheSizeOnLowDiskSpace((100 * ByteConstants.MB).toLong())
                .setMaxCacheSizeOnVeryLowDiskSpace((50 * ByteConstants.MB).toLong())
                .setMaxCacheSize((80 * ByteConstants.MB).toLong()).build()
        val config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setDownsampleEnabled(true)
                .setBitmapMemoryCacheParamsSupplier(BitmapMemoryCacheParamsSupplier(getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager))
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build()
        Fresco.initialize(this, config)
    }

    private fun initBluetoothBracelet(){
        BraceletManagerUtil.instance.initBracelet(object :InitializeListener{
            override fun onInitializeFailure(p0: String?) {
                Log.e("Callback", "初始化失败$p0")
                registerDataListener()  //  注册数据监听

                // 重新连接
                if (AppPrefsUtils.getString(BaseConstant.BRACELET_MAC).isNotEmpty()){
                    BraceletManagerUtil.instance.reConnectBleDevice(object :BraceletManagerUtil.ConnectListener{
                        override fun onConnect() {


                        }
                    })
                }
                //
            }

            override fun onInitializeSuccess() {

            }
        })
    }

    private fun registerDataListener(){
        BraceletManagerUtil.instance.registerDataListener(object :BleDataListener{
            override fun onCallbackFailure(p0: String?) {
                Log.e("Callback", "收到错误数据$p0")

            }

            override fun onDataCallback(callback: Callback<Any>?) {
                Log.e("Callback", "数据监听" + callback!!.data.toString())

                if (callback.mode == CallbackMode.SPORTS_DATA) {
                    val sportsBean = callback.data as SportsBean
                    BraceletData.instance.sportsBean = sportsBean
                    AppPrefsUtils.putString(BaseConstant.BRACELET_TODAY_STEP+DateUtils.getToDay(),sportsBean.step.toString())
                    AppPrefsUtils.putString(BaseConstant.BRACELET_TODAY_CAL+DateUtils.getToDay(),(sportsBean.calorie.toDouble() / 1000.toDouble()).toString())
                }
                if (callback.mode == CallbackMode.SLEEP_DATA) {
                    val sleepBean = callback.data as SleepBean
                    BraceletData.instance.sleepBean = sleepBean
                }
                if (callback.mode == CallbackMode.BATTERY) {
                    val batteryBean = callback.data as BatteryBean
                    BraceletData.instance.batteryBean = batteryBean
                }
                if (callback.mode == CallbackMode.MSG_SWITCH) {
                    val msgSwitchList = callback.data as List<MsgSwith>
                    BraceletData.instance.msgSwitchList = msgSwitchList
                }
                if (callback.mode == CallbackMode.HEART_RATE) {
                    val heartRateBean = callback.data as HeartrateBean
                    BraceletData.instance.heartRateBean = heartRateBean
                }
                if (callback.mode == CallbackMode.BLOOD_OXYGEN) {
                    val bloodOxygenList = callback.data as List<BloodOxygen>
                    BraceletData.instance.bloodOxygenList = bloodOxygenList
                }
                if (callback.mode == CallbackMode.BLOOD_PRESSURE) {
                    val bloodPressureList = callback.data as List<BloodPressure>
                    BraceletData.instance.bloodPressureList = bloodPressureList
                }
                if (callback.mode == CallbackMode.ALL_DAY_HEART_RATE) {
                    val allDayHeartRateBean = callback.data as AllDayHeartRateBean
                    BraceletData.instance.allDayHeartRateBean = allDayHeartRateBean
                }
                if (callback.mode == CallbackMode.FIND_PHONE) {

                }
                if (callback != null) {
                    Bus.send(BraceletDataEvent(callback))
                }
            }
        })
    }
}