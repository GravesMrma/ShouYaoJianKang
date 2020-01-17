package com.wuhanzihai.rbk.ruibeikang.fragment

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.loadImage
import com.wuhanzihai.rbk.ruibeikang.R
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.bluetooth.NotificationService
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.common.setOnBannerListener
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.event.BraceletDataEvent
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerIndexComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.IndexModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemNews
import com.wuhanzihai.rbk.ruibeikang.media.MusicService
import com.wuhanzihai.rbk.ruibeikang.presenter.MainFragmentPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MainView
import com.wuhanzihai.rbk.ruibeikang.utils.BraceletManagerUtil
import com.wuhanzihai.rbk.ruibeikang.utils.JavaUtils
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import com.wuhanzihai.rbk.ruibeikang.utils.TimeUtils
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startService


class MainFragment : BaseMvpFragment<MainFragmentPresenter>(), MainView {

    override fun injectComponent() {
        DaggerIndexComponent.builder().activityComponent(mActivityComponent)
                .indexModule(IndexModule()).build().inject(this)
        mPresenter.mView = this
    }

    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>

    private lateinit var newsList: MutableList<HealthListItem>
    private lateinit var newsAdapter: BaseQuickAdapter<HealthListItem, BaseViewHolder>

    private lateinit var indexClockBean: IndexClockBean

    private var bannerList = mutableListOf<BannerEntity>()
    private var bannerFoodList = mutableListOf<BannerEntity>()

    private var handler = Handler()

    override fun onIndexDate(result: IndexBean) {
        newsList.clear()
        newsList.addAll(result.item)
        newsAdapter.notifyDataSetChanged()
        srView.finishRefresh()
    }

    override fun onIndexAdv(result: IndexAdvBean) {
        ivImgOne.loadImage(result.one.url)
        ivImgTwo.loadImage(result.two.url)
        bannerFoodList.clear()
        bannerFoodList.add(result.theme)
        mBannerFood.update(bannerFoodList)
        srView.finishRefresh()
    }

    override fun onIndexBanner(result: IndexBannerBean) {
        bannerList.clear()
        bannerList.addAll(result.item)
        mBanner.update(bannerList)
        srView.finishRefresh()
    }

    override fun onIndexClock(result: IndexClockBean) {
        indexClockBean = result
        ivImg.loadImage(TimeUtils.getInstance().getHourImgUrl())
        tvText1.text = TimeUtils.getInstance().getHour()
        tvText.text = result.titlle
        tvContent.text = result.description
        srView.finishRefresh()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()

        setAdv()
    }

    private fun initView() {
        mBanner.setImageLoader(FrescoBannerLoader(true))
                .start()
        mBanner.setOnBannerListener { setOnBannerListener(act, bannerList[it]) }

        mBannerFood.setImageLoader(FrescoBannerLoader(true))
                .start()
        mBannerFood.setOnBannerListener { setOnBannerListener(act, bannerFoodList[it]) }


        rlHeathInfo.onClick {
            startActivity<HealthInfoActivity>()
        }
        rlHeathHabits.onClick {
            startActivity<HealthHabitsActivity>()
        }
        rlHeathCheck.onClick {
            startActivity<HealthCheckActivity>()
        }
        rlHeathCare.onClick {
            startActivity<HealthCareActivity>("fatherId" to 3, "title" to "健康医疗")
        }
        clQuestion.onClick {
            startActivity<QuestionActivity>()
        }
        lClock.onClick {
            startActivity<ClockDetailActivity>("data" to indexClockBean.content
                    , "title" to indexClockBean.titlle)
        }
        tvToSearch.onClick {
            startActivity<SearchActivity>()
        }
        ivImgOne.onClick {
            startActivity<HealthClassActivity>()
        }
        ivImgTwo.onClick {
            startActivity<MusicTherapyActivity>()
        }
        tvMoreNews.onClick {
            startActivity<HealthInfoActivity>()
        }
        ivImg1.onClick {
            startActivity<SysMsgActivity>()
        }

        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_main_sport, list) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper!!.setVisible(R.id.tvState, !BraceletManagerUtil.instance.getBleDeviceState())
                when (helper.layoutPosition) {
                    0 -> {
                        helper.setImageResource(R.id.ivType, R.mipmap.foot_icon_bushu)
                        helper.setText(R.id.tvType, "步数")
                    }
                    1 -> {
                        helper.setImageResource(R.id.ivType, R.mipmap.foot_icon_shuimian)
                        helper.setText(R.id.tvType, "睡眠")
                    }
                    2 -> {
                        helper.setImageResource(R.id.ivType, R.mipmap.foot_icon_xueyang)
                        helper.setText(R.id.tvType, "血氧")
                    }
                    3 -> {
                        helper.setImageResource(R.id.ivType, R.mipmap.foot_icon_xinlv)
                        helper.setText(R.id.tvType, "心率")
                    }
                }

                if (BraceletManagerUtil.instance.getBleDeviceState()) {
                    helper.getView<TextView>(R.id.tvValue).visibility = View.VISIBLE
                    helper.getView<TextView>(R.id.tvState).visibility = View.INVISIBLE
                    when (helper.layoutPosition) {
                        0 -> {
                            if (BraceletData.instance.sportsBean != null) {
                                val str = "<strong>${BraceletData.instance.sportsBean!!.step}</strong><br>步"
                                helper.setText(R.id.tvValue, Html.fromHtml(str))
                            } else {
                                val str = "<strong>0</strong><br>步"
                                helper.setText(R.id.tvValue, Html.fromHtml(str))
                            }
                        }
                        1 -> {
                            if (BraceletData.instance.sleepBean != null) {
                                val str = "<strong>${MyUtils.toHour(BraceletData.instance.sleepBean!!.sleepTimeSum)}</strong>时<br><strong>${MyUtils.toMui(BraceletData.instance.sleepBean!!.sleepTimeSum)}</strong>分"
                                helper.setText(R.id.tvValue, Html.fromHtml(str))
                            } else {
                                val str = "0<font><strong>时</strong></font><br>0<font><strong>分</strong></font>"
                                helper.setText(R.id.tvValue, Html.fromHtml(str))
                            }
                        }
                        2 -> {

                            if (BraceletData.instance.bloodOxygenList != null) {
                                val str = "<font><strong>${BraceletData.instance.bloodOxygenList!!.first().value}</strong></font><br>HbO2"
                                helper.setText(R.id.tvValue, Html.fromHtml(str))
                            } else {
                                val str = "<font><strong>0</strong></font><br>HbO2"
                                helper.setText(R.id.tvValue, Html.fromHtml(str))
                            }
//                            if (BraceletData.instance.bloodPressureList != null) {
//                                val str = "<font><strong>${BraceletData.instance.bloodPressureList!!.first().high}/${BraceletData.instance.bloodPressureList!!.first().low}</strong></font><br>mmHg"
//                                helper.setText(R.id.tvValue, Html.fromHtml(str))
//                            } else {
//
//                            }
                        }
                        3 -> {
                            if (BraceletData.instance.heartRateBean != null) {
                                val str = "<strong>${BraceletData.instance.heartRateBean!!.heartrate}</strong><br>bpm"
                                helper.setText(R.id.tvValue, Html.fromHtml(str))
                            } else {
                                val str = "<font><strong>0</strong></font><br>bpm"
                                helper.setText(R.id.tvValue, Html.fromHtml(str))
                            }
                        }
                    }
                } else {
                    helper.getView<TextView>(R.id.tvState).visibility = View.VISIBLE
                    helper.getView<TextView>(R.id.tvValue).visibility = View.INVISIBLE
                }
            }
        }
        rvSport.adapter = adapter
        rvSport.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)
        adapter.setOnItemClickListener { _, _, _ ->
            startActivity<BluetoothSetActivity>()
        }

        newsList = mutableListOf()
        newsAdapter = object : BaseQuickAdapter<HealthListItem, BaseViewHolder>(R.layout.item_home_news, newsList) {
            override fun convert(helper: BaseViewHolder?, item: HealthListItem?) {
                helper!!.setText(R.id.tvDesc, item!!.title)
                        .setText(R.id.tvReadNum, item.click.toString())
                        .setText(R.id.tvJudgeNum, item.follow.toString())
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.thumb)
            }
        }
        rvView.adapter = newsAdapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemNews(act))
        newsAdapter.setOnItemClickListener { _, _, position ->
            startActivity<UnifiedWebActivity>("id" to newsList[position].article_id)
        }

        srView.setOnRefreshListener {
            initData()
        }

        Bus.observe<BraceletDataEvent>().subscribe {
            handler.post {
                adapter.notifyDataSetChanged()
            }
        }
        handler.postDelayed({
            reConnect()
        }, 10000)
        if (isResumed) {
            if (isOpenNotify()) {
                NotificationService.ensureCollectorRunning(act)
            }
        }
    }

    private fun initData() {
        mPresenter.indexBanner()
        mPresenter.indexAdv()
        mPresenter.indexClock()
        mPresenter.indexInfo()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.indexClock()
        adapter.notifyDataSetChanged()
    }

    private fun reConnect() {
        if (AppPrefsUtils.getString(BaseConstant.BRACELET_MAC).isNotEmpty()) {
            if (!BraceletManagerUtil.instance.getBleDeviceState()) {
                if (openBluetooth()) {
                    // 重新连接手环
                    Log.e("Callback", "MainFragment开始自动重新连接")
                    BraceletManagerUtil.instance.reConnectBleDevice(object : BraceletManagerUtil.ConnectListener {
                        override fun onConnect() {


                        }
                    })
                }
            }
            // 检查是否有通知权限
        }
    }

    private fun openBluetooth(): Boolean {
        // 检查设备是否支持蓝牙
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (adapter != null) {
            return adapter.isEnabled
        }
        return false
    }

    private fun isOpenNotify(): Boolean {
        return NotificationService.isEnabled(act)
    }

    private fun setAdv() {
        ivT11.onClick {
            startActivity<StandardWebActivity>("title" to "上海新虹桥"
                    , "data" to BaseConstant.BASE_URL + "api/Web/article?id=895")
        }
        ivT12.onClick {
            startActivity<StandardWebActivity>("title" to "慈铭博鳌"
                    , "data" to BaseConstant.BASE_URL + "api/Web/article?id=893")
        }
        ivT13.onClick {
            startActivity<StandardWebActivity>("title" to "普陀国际"
                    , "data" to BaseConstant.BASE_URL + "api/Web/article?id=896")
        }
        ivT14.onClick {
            startActivity<StandardWebActivity>("title" to "南太湖生命谷"
                    , "data" to BaseConstant.BASE_URL + "api/Web/article?id=898")
        }
        ivT21.onClick {
            startActivity<StandardWebActivity>("title" to "英国医疗"
                    , "data" to BaseConstant.BASE_URL + "api/Web/article?id=891")
        }
        ivT22.onClick {
            startActivity<StandardWebActivity>("title" to "德国医疗"
                    , "data" to BaseConstant.BASE_URL + "api/Web/article?id=892")
        }
        ivT23.onClick {
            startActivity<StandardWebActivity>("title" to "美国医疗"
                    , "data" to BaseConstant.BASE_URL + "api/Web/article?id=894")
        }
        ivT24.onClick {
            startActivity<StandardWebActivity>("title" to "日本医疗"
                    , "data" to BaseConstant.BASE_URL + "api/Web/article?id=890")
        }
        ivT31.onClick {
            startActivity<HealthCareActivity>("fatherId" to 27,
                    "childId" to 35, "title" to "健康医疗")
        }
        ivT32.onClick {
            startActivity<HealthCareActivity>("fatherId" to 27,
                    "childId" to 35, "title" to "健康医疗")
        }
        ivT33.onClick {
            startActivity<HealthCareActivity>("fatherId" to 27,
                    "childId" to 35, "title" to "健康医疗")
        }
        ivT34.onClick {
            startActivity<HealthCareActivity>("fatherId" to 27,
                    "childId" to 35, "title" to "健康医疗")
        }
    }
}