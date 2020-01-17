package com.wuhanzihai.rbk.ruibeikang.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.event.MainEvent
import com.wuhanzihai.rbk.ruibeikang.event.MusicEvent
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemHealthTask
import com.wuhanzihai.rbk.ruibeikang.media.MusicService
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthFragmentPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthFragmentView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_healthy.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startService
import android.location.Criteria
import android.location.LocationManager
import android.os.Handler
import android.support.v4.content.ContextCompat
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.google.gson.Gson
import com.whhh.setloca.util.LocationUtil
import com.wuhanzihai.rbk.ruibeikang.common.setOnBannerListener
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.utils.JavaUtils
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import okhttp3.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class HealthyFragment : BaseMvpFragment<HealthFragmentPresenter>(), HealthFragmentView, EasyPermissions.PermissionCallbacks {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthResult(result: HealthIndexBean) {
        ivZjList.clear()
        ivZjList.addAll(result.doctor.item)
        ivZj.update(ivZjList)

        ivTravelList.clear()
        ivTravelList.add(result.product.item.first())
        ivTravel.update(ivTravelList)

        listTrv.clear()
        listTrv.addAll(result.banner.item)
        adapterTrv.notifyDataSetChanged()

        list.clear()
        list.addAll(result.music.item)
        adapter.notifyDataSetChanged()
        srView.finishRefresh()
    }

    private lateinit var list: MutableList<MusicItem>
    private lateinit var adapter: BaseQuickAdapter<MusicItem, BaseViewHolder>

    private lateinit var listTrv: MutableList<BannerEntity>
    private lateinit var adapterTrv: BaseQuickAdapter<BannerEntity, BaseViewHolder>

    private lateinit var taskList: MutableList<HealthTaskBean>
    private lateinit var taskAdapter: BaseQuickAdapter<HealthTaskBean, BaseViewHolder>

    private lateinit var ivZjList: MutableList<BannerEntity>
    private lateinit var ivTravelList: MutableList<BannerEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_healthy, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setLightMode(act)

        initView()

        initData()

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

                    var url = "https://api.caiyunapp.com/v2/ydJjavRpaxOXcm0h/${lng},${lat}/realtime.json?lang=zh_CN"
//                    var url = "https://api.caiyunapp.com/v2/ydJjavRpaxOXcm0h/${lng},${lat}/daily.json?lang=zh_CN&dailysteps=1"
                    getWeather(url)
                }
            }).start()
        } else {
            EasyPermissions.requestPermissions(this, "你好,APP需要的权限。你能允许吗?", 3333, *perms)
        }
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
                        llWeather.background = ContextCompat.getDrawable(act, R.mipmap.pic_weather3)
                    }
                    if (hour in 6..10) {
                        llWeather.background = ContextCompat.getDrawable(act, R.mipmap.pic_weather1)
                    }
                    if (hour in 11..16) {
                        llWeather.background = ContextCompat.getDrawable(act, R.mipmap.pic_weather2)
                    }

                    tvTemp.text = "${data.result.temperature.toInt()}°"
                    if (data.result.temperature in -100..7) {
                        tvTempType1.text = "极易发"
                    }
                    if (data.result.temperature in 8..15) {
                        tvTempType1.text = "较易发"
                    }
                    if (data.result.temperature in 16..500) {
                        tvTempType1.text = "不易发"
                    }
                    tvTempType2.text = data.result.comfort.desc
                    tvTempType3.text = data.result.ultraviolet.desc
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
                    }
                    if (data.result.aqi in 51..100) {
                        tvTempPM.text = "${data.result.aqi} 良"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp2)
                    }
                    if (data.result.aqi in 101..150) {
                        tvTempPM.text = "${data.result.aqi} 轻度"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp3)
                    }
                    if (data.result.aqi in 151..200) {
                        tvTempPM.text = "${data.result.aqi} 中度"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp4)
                    }
                    if (data.result.aqi in 201..300) {
                        tvTempPM.text = "${data.result.aqi} 重度"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp5)
                    }
                    if (data.result.aqi in 301..500) {
                        tvTempPM.text = "${data.result.aqi} 严重"
                        tvTempPM.background = ContextCompat.getDrawable(act, R.drawable.sp_temp6)
                    }
                }


            }
        })
    }

    private val handler = Handler()

    private fun initView() {
        ivZjList = mutableListOf()
        ivZj.setImageLoader(FrescoBannerLoader(true))
                .start()
//        ivZj.setOnBannerListener { setOnBannerListener(act, ivZjList[it]) }
        ivZj.onClick {
            startActivity<ShareHealthActivity>()
        }

        llWeather.onClick {
            startActivity<WeatherActivity>()
        }

        ivTravelList = mutableListOf()
        ivTravel.setImageLoader(FrescoBannerLoader(true))
                .start()
        ivTravel.onClick {
            startActivity<SingleTravelActivity>()
        }

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<MusicItem, BaseViewHolder>(R.layout.item_music_small, list) {
            override fun convert(helper: BaseViewHolder?, item: MusicItem?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivImage).loadImage(item!!.music_img)
                helper.setText(R.id.tvText1, item.music_title)
                        .setText(R.id.tvText2, item.music_desc)
                        .setText(R.id.tvText3, "${item.music_number}次收听")
            }
        }
        rvMusic.adapter = adapter
        rvMusic.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)
//        rvMusic.addItemDecoration(DividerItem12_14_12(act))
        adapter.setOnItemClickListener { _, _, position ->
            startService<MusicService>("data" to MusicEvent(list, position))
            Bus.send(MainEvent())
        }

        listTrv = mutableListOf()
        adapterTrv = object : BaseQuickAdapter<BannerEntity, BaseViewHolder>(R.layout.item_image_health, listTrv) {
            override fun convert(helper: BaseViewHolder?, item: BannerEntity?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.url)
            }
        }

        dvView.adapter = adapterTrv
        dvView.setItemTransformer(ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.95f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build())
        adapterTrv.setOnItemClickListener { adapter, view, position ->
            when (listTrv[position].linktype) {
                1 -> {
                    startActivity<MusicTherapyActivity>("id" to listTrv[position].link)
                }
                2 -> {
                    startActivity<UnifiedWebActivity>("id" to listTrv[position].link)
                }
                3 -> {
                    startActivity<HealthCallActivity>("id" to listTrv[position].link)
                }
                4 -> {
                    startActivity<GoodsDetailActivity>("id" to listTrv[position].link)
                }
                5 -> {
                    startActivity<HealthClassActivity>()
                }
                6 -> {
                    startActivity<MusicTherapyActivity>()
                }
                7 -> {
                    startActivity<HealthInfoActivity>()
                }
                8 -> {
                    startActivity<SingleTravelActivity>()
                }
            }
        }

        taskList = mutableListOf()
        taskList.add(HealthTaskBean("", false))
        taskList.add(HealthTaskBean("", false))
        taskList.add(HealthTaskBean("", false))
        taskList.add(HealthTaskBean("", false))
        taskList.add(HealthTaskBean("", false))
        taskAdapter = object : BaseQuickAdapter<HealthTaskBean, BaseViewHolder>(R.layout.item_health_task, taskList) {
            override fun convert(helper: BaseViewHolder?, item: HealthTaskBean?) {

            }
        }
        rvView1.adapter = taskAdapter
        rvView1.layoutManager = GridLayoutManager(act, 1)
        rvView1.addItemDecoration(DividerItemHealthTask(act))
//
//        ivTravel.onClick {
//            startActivity<PurifyTravelActivity>()
//        }

//        ivImg.onClick {
//            startActivity<StepRankActivity>()
//        }
        tvMoreMusic.onClick {
            startActivity<MusicTherapyActivity>()
        }

        srView.setOnRefreshListener {
            mPresenter.healthIndex()
        }
        srView.setOnLoadMoreListener {
            srView.setNoMoreData(true)
        }
    }

    // 以下代码 仅供参考 切忌勿改的 否则会直接导致你的智商出BUG  傻逼代码 ！！！
    private var imgs = mutableListOf<String>()

    private fun initData() {
        mPresenter.healthIndex()
//        imgs.add("http://www.hcjiankang.com/androidimg/img1.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img2.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img3.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img4.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img5.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img6.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img7.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img8.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img9.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img10.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img12.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img13.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img14.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img15.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img16.png")
//        imgs.add("http://www.hcjiankang.com/androidimg/img17.png")

//        ivHead.loadImage("http://www.hcjiankang.com/androidimg/img1.png")
//        ivHead1.loadImage("http://www.hcjiankang.com/androidimg/img2.png")
//        ivHead2.loadImage("http://www.hcjiankang.com/androidimg/img3.png")
//        ivHead3.loadImage("http://www.hcjiankang.com/androidimg/img4.png")
//        ivHead4.loadImage("http://www.hcjiankang.com/androidimg/img5.png")
//        ivHead5.loadImage("http://www.hcjiankang.com/androidimg/img6.png")
//        ivImg.loadImage("http://www.hcjiankang.com/androidimg/ic_healthaa.png")
//        suiJi()
//        startAnimation()
    }
//
//    private var index = 0
//    private var indexs = mutableListOf<Int>()
//
//    private fun suiJi() {
//        indexs.clear()
//        while (true) {
//            if (indexs.size == 6) {
//                break
//            }
//            val randoms = (0..15).random()
//            var iscon = false
//            for (index in indexs) {
//                if (index == randoms) {
//                    iscon = true
//                }
//            }
//            if (!iscon) {
//                indexs.add(randoms)
//            }
//        }
//    }
//
//    private fun startAnimation() {
//        val animationSet = AnimationSet(true)
//        val animation = TranslateAnimation(300 - (index % 6 * 50f), 0f, 0f, 0f)
//        val apche = AlphaAnimation(0f, 1f)
//        val rote = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.duration = 2400 - (300 * index % 6.toLong())
//        apche.duration = 2400 - (300 * index % 6.toLong())
//        rote.duration = 2400 - (300 * index % 6.toLong())
//        animationSet.addAnimation(animation)
//        animationSet.addAnimation(apche)
//        animationSet.addAnimation(rote)
//        animationSet.fillAfter = true
//        animationSet.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationRepeat(animation: Animation?) {}
//            override fun onAnimationEnd(animation: Animation?) {
//                if (index == -1) {
//                    return
//                }
//                index++
//                when (index % 6) {
//                    0 -> ivHead.loadImage(imgs[indexs[0]])
//                    1 -> ivHead1.loadImage(imgs[indexs[1]])
//                    2 -> ivHead2.loadImage(imgs[indexs[2]])
//                    3 -> ivHead3.loadImage(imgs[indexs[3]])
//                    4 -> ivHead4.loadImage(imgs[indexs[4]])
//                    5 -> {
//                        ivHead5.loadImage(imgs[indexs[5]])
//                        suiJi()
//                    }
//                }
//                startAnimation()
//            }
//
//            override fun onAnimationStart(animation: Animation?) {}
//        })
//        when (index % 6) {
//            0 -> rl1.startAnimation(animationSet)
//            1 -> rl2.startAnimation(animationSet)
//            2 -> rl3.startAnimation(animationSet)
//            3 -> rl4.startAnimation(animationSet)
//            4 -> rl5.startAnimation(animationSet)
//            5 -> rl6.startAnimation(animationSet)
//        }
//    }
    // 傻逼代码结束
}