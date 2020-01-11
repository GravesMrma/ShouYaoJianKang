package com.whhh.setloca.util

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.hhjt.baselibrary.common.BaseApplication

class LocationUtil {

    private lateinit var client: LocationClient

    companion object {
        val instance by lazy {
            LocationUtil()
        }
    }

    fun registerListener(listener: BDAbstractLocationListener): LocationClient {
        client = LocationClient(BaseApplication.context)

        initLocation()
        client.registerLocationListener(listener)
        return client
    }

    fun restartLocation() {
        client = LocationClient(BaseApplication.context)

        initLocation()
        client.start()
    }

    private fun initLocation(): LocationClient {
        initOption()
        return client
    }

    private fun initOption() {

        var option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true)
//可选，设置返回经纬度坐标类型，默认GCJ02
//GCJ02：国测局坐标；
//BD09ll：百度经纬度坐标；
//BD09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        option.setIsNeedLocationDescribe(true)
        option.setScanSpan(60 * 10 * 1000);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.isOpenGps = true;
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.isLocationNotify = false;
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIsNeedLocationPoiList(true)
//可选，是否需要周边POI信息，默认为不需要，即参数为false
//如果开发者需要获得周边POI信息，此处必须为true

        option.setIgnoreKillProcess(false)
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
//可选，V7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        client.locOption = option;
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    fun openBaiduMap(context: Context, slat: Double, slon: Double, sname: String,
                     dlat: Double, dlon: Double, dname: String, city: String) {
        try {
            val uri = getBaiduMapUri(slat.toString(), slon.toString(), sname,
                    dlat.toString(), dlon.toString(), dname, city, "")
            val intent = Intent.parseUri(uri, 0)
            context.startActivity(intent) //启动调用
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getBaiduMapUri(originLat: String, originLon: String, originName: String, desLat: String, desLon: String, destination: String, region: String, src: String): String {
        val uri = "intent://map/direction?origin=latlng:%1\$s,%2\$s|name:%3\$s" +
                "&destination=latlng:%4\$s,%5\$s|name:%6\$s&mode=driving&region=%7\$s&src=%8\$s#Intent;" +
                "scheme=bdapp;package=com.baidu.BaiduMap;end"
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, src)
    }

    private var lm: LocationManager? = null
    fun checkGPS(context: Context): Boolean {
        lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}