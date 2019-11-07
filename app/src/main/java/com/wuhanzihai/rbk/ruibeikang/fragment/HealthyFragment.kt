package com.wuhanzihai.rbk.ruibeikang.fragment

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
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.common.setOnBannerListener
import com.wuhanzihai.rbk.ruibeikang.data.entity.BannerEntity
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthIndexBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthTaskBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicItem
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

class HealthyFragment : BaseMvpFragment<HealthFragmentPresenter>(), HealthFragmentView {
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
    }

    private fun initView() {
        ivZjList = mutableListOf()
        ivZj.setImageLoader(FrescoBannerLoader(true))
                .start()
//        ivZj.setOnBannerListener { setOnBannerListener(act, ivZjList[it]) }
        ivZj.onClick {
            startActivity<StandardWebActivity>("title" to "专家问诊"
                    , "data" to "http://www.hcjiankang.com/androidimg/wenzheng.html")
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

        ivImg.onClick {
            startActivity<StepRankActivity>()
//
//            startActivity<StandardWebActivity>("title" to "健康打卡"
//                    , "data" to "http://www.hcjiankang.com/androidimg/sign.html")
        }
        tvMoreMusic.onClick {
            startActivity<MusicTherapyActivity>()
        }

        srView.setOnRefreshListener {
            mPresenter.healthIndex()
        }
    }

    // 以下代码 仅供参考 切忌勿改的 否则会直接导致你的智商出BUG
    private var imgs = mutableListOf<String>()

    private fun initData() {
        mPresenter.healthIndex()
        imgs.add("http://www.hcjiankang.com/androidimg/img1.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img2.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img3.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img4.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img5.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img6.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img7.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img8.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img9.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img10.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img12.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img13.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img14.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img15.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img16.png")
        imgs.add("http://www.hcjiankang.com/androidimg/img17.png")

        ivHead.loadImage("http://www.hcjiankang.com/androidimg/img1.png")
        ivHead1.loadImage("http://www.hcjiankang.com/androidimg/img2.png")
        ivHead2.loadImage("http://www.hcjiankang.com/androidimg/img3.png")
        ivHead3.loadImage("http://www.hcjiankang.com/androidimg/img4.png")
        ivHead4.loadImage("http://www.hcjiankang.com/androidimg/img5.png")
        ivHead5.loadImage("http://www.hcjiankang.com/androidimg/img6.png")
        ivImg.loadImage("http://www.hcjiankang.com/androidimg/ic_healthaa.png")
        suiJi()
        startAnimation()
    }

    private var index = 0
    private var indexs = mutableListOf<Int>()

    private fun suiJi() {
        indexs.clear()
        while (true) {
            if (indexs.size == 6) {
                break
            }
            val randoms = (0..15).random()
            var iscon = false
            for (index in indexs) {
                if (index == randoms) {
                    iscon = true
                }
            }
            if (!iscon) {
                indexs.add(randoms)
            }
        }
    }

    private fun startAnimation() {
        val animationSet = AnimationSet(true)
        val animation = TranslateAnimation(300 - (index % 6 * 50f), 0f, 0f, 0f)
        val apche = AlphaAnimation(0f, 1f)
        val rote = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.duration = 2400 - (300 * index % 6.toLong())
        apche.duration = 2400 - (300 * index % 6.toLong())
        rote.duration = 2400 - (300 * index % 6.toLong())
        animationSet.addAnimation(animation)
        animationSet.addAnimation(apche)
        animationSet.addAnimation(rote)
        animationSet.fillAfter = true
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                if (index == -1) {
                    return
                }
                index++
                when (index % 6) {
                    0 -> ivHead.loadImage(imgs[indexs[0]])
                    1 -> ivHead1.loadImage(imgs[indexs[1]])
                    2 -> ivHead2.loadImage(imgs[indexs[2]])
                    3 -> ivHead3.loadImage(imgs[indexs[3]])
                    4 -> ivHead4.loadImage(imgs[indexs[4]])
                    5 -> {
                        ivHead5.loadImage(imgs[indexs[5]])
                        suiJi()
                    }
                }
                startAnimation()

                Log.e("随机数", indexs.toString())
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        when (index % 6) {
            0 -> rl1.startAnimation(animationSet)
            1 -> rl2.startAnimation(animationSet)
            2 -> rl3.startAnimation(animationSet)
            3 -> rl4.startAnimation(animationSet)
            4 -> rl5.startAnimation(animationSet)
            5 -> rl6.startAnimation(animationSet)
        }
    }
    // 傻逼代码结束
}