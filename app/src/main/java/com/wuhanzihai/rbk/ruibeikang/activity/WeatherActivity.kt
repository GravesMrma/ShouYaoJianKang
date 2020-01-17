package com.wuhanzihai.rbk.ruibeikang.activity

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.whhh.setloca.util.LocationUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.PreWeatherBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.PreWeatherItemBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.WeatherBean
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import com.wuhanzihai.rbk.ruibeikang.widgets.LineView
import kotlinx.android.synthetic.main.activity_weather.*
import okhttp3.*
import org.jetbrains.anko.act
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var list: MutableList<PreWeatherItemBean>
    private lateinit var adapter: BaseQuickAdapter<PreWeatherItemBean, BaseViewHolder>

    private var mmax = -100
    private var mmin = 100
    private var mindex = 0
    private var imgs = mutableListOf(R.mipmap.pic_weather_ic1,
            R.mipmap.pic_weather_ic3,
            R.mipmap.pic_weather_ic2,
            R.mipmap.pic_weather_ic15,
            R.mipmap.pic_weather_ic20,
            R.mipmap.pic_weather_ic5,
            R.mipmap.pic_weather_ic11)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private var soc = 0

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<PreWeatherItemBean, BaseViewHolder>(R.layout.item_weather, list) {
            override fun convert(helper: BaseViewHolder?, item: PreWeatherItemBean?) {
                helper!!.setText(R.id.tvTime, item!!.time)
                        .setText(R.id.tvDate, item.date)
                        .setText(R.id.tvAqi, item.aqi.toString())
                        .setImageResource(R.id.ivWeaType, imgs[item.index])

                var tvAqi = helper.getView<TextView>(R.id.tvAqi)
                if (item.aqi in 0..50) {
                    tvAqi.text = "${item.aqi}\n优"
                    tvAqi.background = ContextCompat.getDrawable(act, R.drawable.sp_temp11)
                }
                if (item.aqi in 51..100) {
                    tvAqi.text = "${item.aqi}\n良"
                    tvAqi.background = ContextCompat.getDrawable(act, R.drawable.sp_temp22)
                }
                if (item.aqi in 101..150) {
                    tvAqi.text = "${item.aqi}\n轻度"
                    tvAqi.background = ContextCompat.getDrawable(act, R.drawable.sp_temp33)
                }
                if (item.aqi in 151..200) {
                    tvAqi.text = "${item.aqi}\n中度"
                    tvAqi.background = ContextCompat.getDrawable(act, R.drawable.sp_temp44)
                }
                if (item.aqi in 201..300) {
                    tvAqi.text = "${item.aqi}\n重度"
                    tvAqi.background = ContextCompat.getDrawable(act, R.drawable.sp_temp55)
                }
                if (item.aqi in 301..500) {
                    tvAqi.text = "${item.aqi}\n严重"
                    tvAqi.background = ContextCompat.getDrawable(act, R.drawable.sp_temp66)
                }
                if (helper.layoutPosition == 0) {
                    Log.e("layoutPosition1", "${helper.layoutPosition}")
                    helper.getView<LineView>(R.id.lvView).setData(item.max.toString(),
                            (list[helper.layoutPosition].max - mmin) / mindex.toFloat(),
                            (list[helper.layoutPosition].max - mmin) / mindex.toFloat(),
                            ((list[helper.layoutPosition + 1].max - mmin) / mindex.toFloat() + (list[helper.layoutPosition].max - mmin) / mindex.toFloat()) / 2,
                            item.min.toString(),
                            (list[helper.layoutPosition].min - mmin) / mindex.toFloat(),
                            (list[helper.layoutPosition].min - mmin) / mindex.toFloat(),
                            ((list[helper.layoutPosition + 1].min - mmin) / mindex.toFloat() + (list[helper.layoutPosition].min - mmin) / mindex.toFloat()) / 2
                    )
                } else if (helper.layoutPosition == list.size - 1) {
                    Log.e("layoutPosition2", "${helper.layoutPosition}")
                    helper.getView<LineView>(R.id.lvView).setData(item.max.toString(),
                            ((list[helper.layoutPosition - 1].max - mmin) / mindex.toFloat() + (list[helper.layoutPosition].max - mmin) / mindex.toFloat()) / 2,
                            (list[helper.layoutPosition].max - mmin) / mindex.toFloat(),
                            (list[helper.layoutPosition].max - mmin) / mindex.toFloat(),
                            item.min.toString(),
                            ((list[helper.layoutPosition - 1].min - mmin) / mindex.toFloat() + (list[helper.layoutPosition].min - mmin) / mindex.toFloat()) / 2,
                            (list[helper.layoutPosition].min - mmin) / mindex.toFloat(),
                            (list[helper.layoutPosition].min - mmin) / mindex.toFloat()
                    )
                } else {
                    Log.e("layoutPosition3", "${helper.layoutPosition}")

                    helper.getView<LineView>(R.id.lvView).setData(item.max.toString(),
                            ((list[helper.layoutPosition - 1].max - mmin) / mindex.toFloat() + (list[helper.layoutPosition].max - mmin) / mindex.toFloat()) / 2,
                            (list[helper.layoutPosition].max - mmin) / mindex.toFloat(),
                            ((list[helper.layoutPosition + 1].max - mmin) / mindex.toFloat() + (list[helper.layoutPosition].max - mmin) / mindex.toFloat()) / 2,
                            item.min.toString(),
                            ((list[helper.layoutPosition - 1].min - mmin) / mindex.toFloat() + (list[helper.layoutPosition].min - mmin) / mindex.toFloat()) / 2,
                            (list[helper.layoutPosition].min - mmin) / mindex.toFloat(),
                            ((list[helper.layoutPosition + 1].min - mmin) / mindex.toFloat() + (list[helper.layoutPosition].min - mmin) / mindex.toFloat()) / 2
                    )
                }


            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)

        tvChange.onClick {
            when (soc) {
                0 -> soc = 9
                9 -> soc = 14
                14 -> soc = 0
            }
            rvView.scrollToPosition(soc)
        }
    }

    private fun initData() {
        getLocation()
    }

    private fun getLocation() {
        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (EasyPermissions.hasPermissions(act, *perms)) {
            LocationUtil.instance.registerListener(object : BDAbstractLocationListener() {
                override fun onReceiveLocation(location: BDLocation?) {
                    if (location == null) {
                        return
                    }
                    var lat = location.latitude.toString()
                    var lng = location.longitude.toString()
                    Log.e("定位", "经${lat}纬${lng}度${location.address.address}")

                    var url1 = "https://api.caiyunapp.com/v2/ydJjavRpaxOXcm0h/${lng},${lat}/realtime.json?lang=zh_CN"
                    var url2 = "https://api.caiyunapp.com/v2/ydJjavRpaxOXcm0h/${lng},${lat}/daily.json?lang=zh_CN&dailysteps=15"
                    getWeather(url1)
                    getPreWeather(url2)
                }
            }).start()
        } else {
            EasyPermissions.requestPermissions(this, "你好,APP需要的权限。你能允许吗?", 3333, *perms)
        }
    }

    private val handler = Handler()

    private fun getPreWeather(url: String) {
        var httpClient = OkHttpClient()
        var request = Request.Builder().url(url)
                .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                var result = response.body()!!.string()
                Log.e("定位", result)
                handler.post {
                    var gson = Gson()
                    var data = gson.fromJson(result, PreWeatherBean::class.java)

                    tvTempToday.text = "${data.result.daily.temperature.first().min.toInt()}° ~ ${data.result.daily.temperature.first().max.toInt()}°"
                    tvTempTomday.text = "${data.result.daily.temperature[1].min.toInt()}° ~ ${data.result.daily.temperature[1].max.toInt()}°"

                    if (data.result.daily.aqi[1].avg in 0..50) {
                        tvTomAir.text = "${data.result.daily.aqi[1].avg.toInt()} 优"
                        tvTomAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp1)
                    }
                    if (data.result.daily.aqi[1].avg in 51..100) {
                        tvTomAir.text = "${data.result.daily.aqi[1].avg.toInt()} 良"
                        tvTomAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp2)
                    }
                    if (data.result.daily.aqi[1].avg in 101..150) {
                        tvTomAir.text = "${data.result.daily.aqi[1].avg.toInt()} 轻度"
                        tvTomAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp3)
                    }
                    if (data.result.daily.aqi[1].avg in 151..200) {
                        tvTomAir.text = "${data.result.daily.aqi[1].avg.toInt()} 中度"
                        tvTomAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp4)
                    }
                    if (data.result.daily.aqi[1].avg in 201..300) {
                        tvTomAir.text = "${data.result.daily.aqi[1].avg.toInt()} 重度"
                        tvTomAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp5)
                    }
                    if (data.result.daily.aqi[1].avg in 301..500) {
                        tvTomAir.text = "${data.result.daily.aqi[1].avg.toInt()} 严重"
                        tvTomAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp6)
                    }
                    when (data.result.daily.skycon.first().value) {
                        "CLEAR_DAY" -> ivToday.setImageResource(R.mipmap.pic_weather_ic1)
                        "CLEAR_NIGHT" -> ivToday.setImageResource(R.mipmap.pic_weather_ic1)
                        "PARTLY_CLOUDY_DAY" -> ivToday.setImageResource(R.mipmap.pic_weather_ic3)
                        "PARTLY_CLOUDY_NIGHT" -> ivToday.setImageResource(R.mipmap.pic_weather_ic3)
                        "CLOUDY" -> ivToday.setImageResource(R.mipmap.pic_weather_ic2)
                        "WIND" -> ivToday.setImageResource(R.mipmap.pic_weather_ic15)
                        "HAZE" -> ivToday.setImageResource(R.mipmap.pic_weather_ic20)
                        "RAIN" -> ivToday.setImageResource(R.mipmap.pic_weather_ic5)
                        "SNOW" -> ivToday.setImageResource(R.mipmap.pic_weather_ic11)
                    }

                    when (data.result.daily.skycon[1].value) {
                        "CLEAR_DAY" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic1)
                        "CLEAR_NIGHT" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic1)
                        "PARTLY_CLOUDY_DAY" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic3)
                        "PARTLY_CLOUDY_NIGHT" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic3)
                        "CLOUDY" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic2)
                        "WIND" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic15)
                        "HAZE" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic20)
                        "RAIN" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic5)
                        "SNOW" -> ivTomday.setImageResource(R.mipmap.pic_weather_ic11)
                    }

                    tvTempType1.text = data.result.daily.coldRisk.first().desc
                    tvTempType2.text = data.result.daily.dressing.first().desc
                    tvTempType3.text = data.result.daily.ultraviolet.first().desc
                    tvTempType4.text = data.result.daily.carWashing.first().desc

                    for (i in 0..14) {
                        list.add(PreWeatherItemBean(getWeekOfDate(i), "", 0, 0, 0, 0))
                    }

                    for (i in data.result.daily.temperature.indices) {
                        list[i].date = data.result.daily.temperature[i].date.substring(5)
                        list[i].max = data.result.daily.temperature[i].max.toInt()
                        list[i].min = data.result.daily.temperature[i].min.toInt()
                        if (list[i].min <= mmin) {
                            mmin = list[i].min
                        }
                        if (list[i].max >= mmax) {
                            mmax = list[i].max
                        }
                    }
                    mindex = mmax - mmin

                    for (i in data.result.daily.aqi.indices) {
                        list[i].aqi = data.result.daily.aqi[i].avg.toInt()
                    }
                    for (i in data.result.daily.skycon.indices) {
                        when (data.result.daily.skycon[i].value) {
                            "CLEAR_DAY" -> list[i].index = 0
                            "CLEAR_NIGHT" -> list[i].index = 0
                            "PARTLY_CLOUDY_DAY" -> list[i].index = 1
                            "PARTLY_CLOUDY_NIGHT" -> list[i].index = 1
                            "CLOUDY" -> list[i].index = 2
                            "WIND" -> list[i].index = 3
                            "HAZE" -> list[i].index = 4
                            "RAIN" -> list[i].index = 5
                            "SNOW" -> list[i].index = 6
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun getWeather(url: String) {
        var httpClient = OkHttpClient()
        var request = Request.Builder().url(url)
                .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                var result = response.body()!!.string()
                Log.e("定位", result)
                handler.post {
                    var gson = Gson()
                    var data = gson.fromJson(result, WeatherBean::class.java)
                    var hour = SimpleDateFormat("HH").format(Date(System.currentTimeMillis())).toInt()

                    if (hour < 6 || hour > 16) {
                        llWeather.background = ContextCompat.getDrawable(act, R.mipmap.pic_weather33)
                        tvTemp.setTextColor(ContextCompat.getColor(act,R.color.white))
                        tvTempDesc.setTextColor(ContextCompat.getColor(act,R.color.white))
                        tvTempPM.setTextColor(ContextCompat.getColor(act,R.color.white))
                        tvTempWind.setTextColor(ContextCompat.getColor(act,R.color.white))
                    }
                    if (hour in 6..10) {
                        llWeather.background = ContextCompat.getDrawable(act, R.mipmap.pic_weather11)
                        tvTemp.setTextColor(ContextCompat.getColor(act,R.color.black_33))
                        tvTempDesc.setTextColor(ContextCompat.getColor(act,R.color.black_33))
                        tvTempPM.setTextColor(ContextCompat.getColor(act,R.color.black_33))
                        tvTempWind.setTextColor(ContextCompat.getColor(act,R.color.black_33))
                    }
                    if (hour in 11..16) {
                        llWeather.background = ContextCompat.getDrawable(act, R.mipmap.pic_weather22)
                        tvTemp.setTextColor(ContextCompat.getColor(act,R.color.black_33))
                        tvTempDesc.setTextColor(ContextCompat.getColor(act,R.color.black_33))
                        tvTempPM.setTextColor(ContextCompat.getColor(act,R.color.black_33))
                        tvTempWind.setTextColor(ContextCompat.getColor(act,R.color.black_33))
                    }
//
                    tvTemp.text = "${data.result.temperature.toInt()}°"
//                    if (data.result.temperature in -100..7) {
////                        tvTempType1.text = "极易发"
//                    }
//                    if (data.result.temperature in 8..15) {
////                        tvTempType1.text = "较易发"
//                    }
//                    if (data.result.temperature in 16..500) {
////                        tvTempType1.text = "不易发"
//                    }
//                    tvTempType2.text = data.result.comfort.desc
//                    tvTempType3.text = data.result.ultraviolet.desc
                    when (data.result.skycon) {
                        "CLEAR_DAY" -> tvTempDesc.text = "晴（白天）"
                        "CLEAR_NIGHT" -> tvTempDesc.text = "晴（夜间）"
                        "PARTLY_CLOUDY_DAY" -> tvTempDesc.text = "多云（白天）"
                        "PARTLY_CLOUDY_NIGHT" -> tvTempDesc.text = "多云（夜间）"
                        "CLOUDY" -> tvTempDesc.text = "阴"
                        "WIND" -> tvTempDesc.text = "大风"
                        "HAZE" -> tvTempDesc.text = "雾霾"
                        "RAIN" -> tvTempDesc.text = "雨"
                        "SNOW" -> tvTempDesc.text = "雪"

                    }
                    if (data.result.wind.direction > 348.76 || data.result.wind.direction < 11.25) {
                        tvTempWind.text = "北风 ${MyUtils.instance.getWindLevel(data.result.wind.speed)}   湿度${data.result.humidity * 100}%"
                    }
                    if (data.result.wind.direction in 11.26..78.75) {
                        tvTempWind.text = "东北风 ${MyUtils.instance.getWindLevel(data.result.wind.speed)}   湿度${data.result.humidity * 100}%"
                    }
                    if (data.result.wind.direction in 78.76..101.25) {
                        tvTempWind.text = "东风 ${MyUtils.instance.getWindLevel(data.result.wind.speed)}   湿度${data.result.humidity * 100}%"
                    }
                    if (data.result.wind.direction in 101.26..168.75) {
                        tvTempWind.text = "东南风 ${MyUtils.instance.getWindLevel(data.result.wind.speed)}   湿度${data.result.humidity * 100}%"
                    }
                    if (data.result.wind.direction in 168.76..191.25) {
                        tvTempWind.text = "南风 ${MyUtils.instance.getWindLevel(data.result.wind.speed)}   湿度${data.result.humidity * 100}%"
                    }
                    if (data.result.wind.direction in 191.26..258.75) {
                        tvTempWind.text = "西南风 ${MyUtils.instance.getWindLevel(data.result.wind.speed)}   湿度${data.result.humidity * 100}%"
                    }
                    if (data.result.wind.direction in 258.76..281.25) {
                        tvTempWind.text = "西风 ${MyUtils.instance.getWindLevel(data.result.wind.speed)}   湿度${data.result.humidity * 100}%"
                    }
                    if (data.result.wind.direction in 281.26..348.75) {
                        tvTempWind.text = "西北风 ${MyUtils.instance.getWindLevel(data.result.wind.speed)} 湿度${data.result.humidity * 100}%"
                    }

                    if (data.result.aqi in 0..50) {

                        tvTempPM.text = "${data.result.aqi} 优"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp1)
                        tvAir.text = "${data.result.aqi} 优"
                        tvAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp1)
                    }
                    if (data.result.aqi in 51..100) {
                        tvTempPM.text = "${data.result.aqi} 良"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp2)
                        tvAir.text = "${data.result.aqi} 良"
                        tvAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp2)
                    }
                    if (data.result.aqi in 101..150) {
                        tvTempPM.text = "${data.result.aqi} 轻度"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp3)
                        tvAir.text = "${data.result.aqi} 轻度"
                        tvAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp3)
                    }
                    if (data.result.aqi in 151..200) {
                        tvTempPM.text = "${data.result.aqi} 中度"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp4)
                        tvAir.text = "${data.result.aqi} 中度"
                        tvAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp4)
                    }
                    if (data.result.aqi in 201..300) {
                        tvTempPM.text = "${data.result.aqi} 重度"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp5)
                        tvAir.text = "${data.result.aqi} 重度"
                        tvAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp5)
                    }
                    if (data.result.aqi in 301..500) {
                        tvTempPM.text = "${data.result.aqi} 严重"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp6)
                        tvAir.text = "${data.result.aqi} 严重"
                        tvAir.background = ContextCompat.getDrawable(act, R.drawable.sp_temp6)
                    }
                }
            }
        })
    }

    private fun getWeekOfDate(index: Int): String {
        var weekDays = mutableListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        var cal = Calendar.getInstance()
        cal.time = Date(System.currentTimeMillis())
        var w = cal.get(Calendar.DAY_OF_WEEK) - 1
        if (index == 0) {
            return "今天"
        }
        if (index == 1) {
            return "明天"
        }
        if (index == 2) {
            return "后天"
        }
        return weekDays[(w + index) % 7]
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 3333) {
            getLocation()
        }
    }
}
