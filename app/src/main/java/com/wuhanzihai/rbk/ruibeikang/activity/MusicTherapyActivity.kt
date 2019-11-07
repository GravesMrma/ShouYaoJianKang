package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.common.toDoubleInt
import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicClassBean
import com.wuhanzihai.rbk.ruibeikang.event.MusicStateEvent
import com.wuhanzihai.rbk.ruibeikang.fragment.MusicFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.media.AudioPlayer
import com.wuhanzihai.rbk.ruibeikang.media.ManagedMediaPlayer
import com.wuhanzihai.rbk.ruibeikang.media.MusicService
import com.wuhanzihai.rbk.ruibeikang.presenter.MusicPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MusicView
import com.wuhanzihai.rbk.ruibeikang.utils.BraceletManagerUtil
import com.wuhanzihai.rbk.ruibeikang.utils.WindowUtil
import kotlinx.android.synthetic.main.activity_music_therapy.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startService
import per.goweii.anylayer.AnyLayer
import java.util.*

class MusicTherapyActivity : BaseMvpActivity<MusicPresenter>(), MusicView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onMusicClassResult(result: MutableList<MusicClassBean>) {
        list.addAll(result)
        list.first().isCheck = true
        adapter.notifyDataSetChanged()
//        mStack.add(MusicHistoryFragment())
        for (musicClassBean in list) {
            mStack.add(MusicFragment(musicClassBean.cat_id))
        }
        pageAdapter.notifyDataSetChanged()

        for (i in 0 until list.size) {
            if (intent.getIntExtra("id", -1) == list[i].cat_id) {
                vpView.currentItem = i
            }
        }
    }

    private val mStack = Stack<Fragment>()

    private lateinit var adapter: BaseQuickAdapter<MusicClassBean, BaseViewHolder>
    private lateinit var list: MutableList<MusicClassBean>
    private lateinit var pageAdapter: FragmentPagerAdapter

    private val dialog by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_timing)
                        .gravity(Gravity.TOP)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(true)
                        .onClick(R.id.tvCancel) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
        anyLayer.getView<RadioGroup>(R.id.mRgRecord).setOnCheckedChangeListener { group, checkedId ->
            if (AudioPlayer.getInstance().getStatus() == ManagedMediaPlayer.Status.STARTED) {
                when (checkedId) {
                    R.id.mRbClose -> {
                        stopCount()
                    }
                    R.id.mRb10 -> {
                        startService<MusicService>("time" to 10 * 60 * 1000)
                        BaseConstant.COUNT_REAL_TIME = System.currentTimeMillis() + 10 * 60 * 1000
                        startCount()
                    }
                    R.id.mRb20 -> {
                        startService<MusicService>("time" to 20 * 60 * 1000)
                        BaseConstant.COUNT_REAL_TIME = System.currentTimeMillis() + 20 * 60 * 1000
                        startCount()
                    }
                    R.id.mRb30 -> {
                        startService<MusicService>("time" to 30 * 60 * 1000)
                        BaseConstant.COUNT_REAL_TIME = System.currentTimeMillis() + 30 * 60 * 1000
                        startCount()
                    }
                    R.id.mRb60 -> {
                        startService<MusicService>("time" to 60 * 60 * 1000)
                        BaseConstant.COUNT_REAL_TIME = System.currentTimeMillis() + 60 * 60 * 1000
                        startCount()
                    }
                    R.id.mRb90 -> {
                        startService<MusicService>("time" to 90 * 60 * 1000)
                        BaseConstant.COUNT_REAL_TIME = System.currentTimeMillis() + 90 * 60 * 1000
                        startCount()
                    }
                }
                anyLayer.dismiss()
            } else {
                showTextDesc(act, "音乐未播放,不能使用定时功能")
            }
        }
        anyLayer
    }

    private var timeRun = Runnable {
        startCount()
    }

    private fun startCount() {
        var a = BaseConstant.COUNT_REAL_TIME - System.currentTimeMillis()
        if (a <= 0) {
            stopCount()
            return
        }
        a /= 1000
        tvTime.text = "${toDoubleInt(a.toInt() / 60)}:${toDoubleInt(a.toInt() % 60)}"
        handler.postDelayed(timeRun, 1000)
    }

    private fun stopCount() {
        tvTime.text = "定时"
        handler.removeCallbacks(timeRun)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_therapy)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()

        tvTime.onClick {
            dialog.show()
        }

        adapter = object : BaseQuickAdapter<MusicClassBean, BaseViewHolder>(R.layout.item_health_title, list) {
            override fun convert(helper: BaseViewHolder?, item: MusicClassBean?) {
                helper!!.setText(R.id.tvText, item!!.cat_name)
                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)

        adapter.setOnItemClickListener { _, _, position ->
            if (!list[position].isCheck) {
                for (musicClassBean in list) {
                    musicClassBean.isCheck = false
                }
                list[position].isCheck = true
                adapter.notifyDataSetChanged()
                vpView.currentItem = position
            }
        }

        pageAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mStack[position]
            }

            override fun getCount(): Int {
                return mStack.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return null
            }
        }
        vpView.adapter = pageAdapter
        vpView.setNoFocus(false)

        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                for (musicClassBean in list) {
                    musicClassBean.isCheck = false
                }
                list[p0].isCheck = true
                adapter.notifyDataSetChanged()

                if (last > p0) { // 向左滑动
                    var a = p0 - 1
                    if (p0 < 0) a = 0
                    rvView.scrollToPosition(a)

                } else { // 向右滑动
                    var a = p0 + 1
                    if (p0 >= mStack.size) a = mStack.size - 1
                    rvView.scrollToPosition(a)
                }
                last = p0
            }
        })

        ivPlay.onClick {
            if (AudioPlayer.getInstance().getData() != null) {
                ivPlay.isSelected = !ivPlay.isSelected
                if (ivPlay.isSelected) {
                    AudioPlayer.getInstance().resume()
                } else {
                    AudioPlayer.getInstance().pause()
                }
            }
        }
        ivLast.onClick {
            if (AudioPlayer.getInstance().getData() != null) {
                AudioPlayer.getInstance().previous()
            }
        }
        ivNext.onClick {
            if (AudioPlayer.getInstance().getData() != null) {
                AudioPlayer.getInstance().next()
            }
        }
        ivOrder.onClick {
            ivOrder.isSelected = !ivOrder.isSelected
            if (ivOrder.isSelected) {
                AudioPlayer.getInstance().playMode = AudioPlayer.PlayMode.REPEAT  // 单曲
            } else {
                AudioPlayer.getInstance().playMode = AudioPlayer.PlayMode.ORDER // 顺序
            }
        }
    }

    private var last = -1

    private fun initData() {
        mPresenter.musicClass()
        Bus.observe<MusicStateEvent>().subscribe {
            ivPlay.isSelected = it.state == ManagedMediaPlayer.Status.STARTED
            when (it.state) {
                ManagedMediaPlayer.Status.STARTED -> {
                    runOnUiThread {
                        clView.visibility = View.VISIBLE
                    }
                    var source = AudioPlayer.getInstance().getNowPlaying()
                    if (!isFinishing) {
                        if (source != null) {
                            ivImg.loadImage(source!!.music_img)
                            tvName.text = source.music_title
                            startSeek()
                        }
                    }
                }
            }
        }.registerInBus(this)
    }

    private var handler = Handler()

    private var run = Runnable {
        startSeek()
    }

    private fun startSeek() {
        tvDuration.text = toDoubleInt(AudioPlayer.getInstance().getCurrentPosition() / 1000 / 60) + ":" + toDoubleInt(AudioPlayer.getInstance().getCurrentPosition() / 1000 % 60)
        tvEnd.text = toDoubleInt(AudioPlayer.getInstance().getDuration() / 1000 / 60) + ":" + toDoubleInt(AudioPlayer.getInstance().getDuration() / 1000 % 60)
        pbView.progress = ((AudioPlayer.getInstance().getCurrentPosition().toDouble() / AudioPlayer.getInstance().getDuration().toDouble()) * 100).toInt()
        handler.postDelayed(run, 500)
    }

    override fun onResume() {
        super.onResume()
        if (AudioPlayer.getInstance().getNowPlaying() != null) {
            clView.visibility = View.VISIBLE
            ivImg.loadImage(AudioPlayer.getInstance().getNowPlaying()!!.music_img)
            tvName.text = AudioPlayer.getInstance().getNowPlaying()!!.music_title
            ivPlay.isSelected = AudioPlayer.getInstance().getStatus() == ManagedMediaPlayer.Status.STARTED
            if (AudioPlayer.getInstance().getStatus() == ManagedMediaPlayer.Status.STARTED) {
                handler.postDelayed(run, 500)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(run)
    }
}
