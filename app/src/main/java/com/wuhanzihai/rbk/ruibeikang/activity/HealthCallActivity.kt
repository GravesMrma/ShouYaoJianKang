package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.common.toDoubleInt
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthClassDetailMusicReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthClassDetailReq
import com.wuhanzihai.rbk.ruibeikang.event.MusicEvent
import com.wuhanzihai.rbk.ruibeikang.event.MusicStateEvent
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthClassMusicFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.WebFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.media.AudioPlayer
import com.wuhanzihai.rbk.ruibeikang.media.ManagedMediaPlayer
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthClassPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthClassView
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_health_call.*
import org.jetbrains.anko.act
import java.util.*

class HealthCallActivity : BaseMvpActivity<HealthClassPresenter>(), HealthClassView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthClassResult(result: HealthClassBean) {
    }

    override fun onHealthBannerResult(result: HealthClassBannerBean) {
    }

    override fun onHealthClassDetailResult(result: HealthClassDetailBean) {
        ivImg.loadImage(result.course_banner)
        tvTitle1.text = result.course_title
        tvTitle2.text = result.course_title
        tvTitle.text = result.course_title
    }

    override fun onHealthClassDetailMusicResult(result: HealthClassDetailMusicBean) {

    }

    private val mStack = Stack<Fragment>()
    private var page = 1

    private lateinit var healthClassMusicFragment: HealthClassMusicFragment

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_call)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)
        StatusBarUtil.setLightMode(act)

        rlView.background.mutate().alpha = 0
        fake_status_bar.background.mutate().alpha = 0

        initView()

        initData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        if (intent.getBooleanExtra("faker", false)) {
            tvLisNum.text = "${intent.getStringExtra("lisnum")}次收听"
        }
        healthClassMusicFragment = HealthClassMusicFragment(intent.getIntExtra("id", -1))
        mStack.add(WebFragment(intent.getIntExtra("id", -1)))
        mStack.add(healthClassMusicFragment)

        var adapter = object : FragmentPagerAdapter(supportFragmentManager) {
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
        vpView.adapter = adapter
        vpView.setNoFocus(true)
        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                vpView.currentItem = p0
                if (p0 == 0){
                    mRbInfo.isSelected = true
                    mRbInfo1.isSelected = true
                    mRbCall.isSelected = false
                    mRbCall1.isSelected = false
                }
                if (p0 == 1){
                    mRbInfo.isSelected = false
                    mRbInfo1.isSelected = false
                    mRbCall.isSelected = true
                    mRbCall1.isSelected = true
                }
            }
        })

        mRbInfo.isSelected = true
        mRbInfo1.isSelected = true
        mRbCall.isSelected = false
        mRbCall1.isSelected = false

        mRbInfo.setOnClickListener {
            vpView.currentItem = 0
        }
        mRbCall.setOnClickListener {
            vpView.currentItem = 1
        }
        mRbInfo1.setOnClickListener {
            vpView.currentItem = 0
        }
        mRbCall1.setOnClickListener {
            vpView.currentItem = 1
        }

        ivBack.onClick {
            finish()
        }
        ivShare.onClick {

        }
        ivCollect.onClick {

        }

        ivPlay.onClick {
            ivPlay.isSelected = !ivPlay.isSelected
            if (ivPlay.isSelected) {
                AudioPlayer.getInstance().resume()
            } else {
                AudioPlayer.getInstance().pause()
            }
        }
        ivLast.onClick {
            AudioPlayer.getInstance().previous()
        }
        ivNext.onClick {
            AudioPlayer.getInstance().next()
        }
        ivOrder.onClick {
            ivOrder.isSelected = !ivOrder.isSelected
        }


        var index = (MyUtils.getWidth(act) / 1.73).toInt()
        var sy = 0
        nsView.viewTreeObserver.addOnScrollChangedListener {
            sy = nsView.scrollY
            if (sy in 0..index) {
                var aaa = ((sy.toFloat() / index.toFloat()) * 255).toInt()
                rlView.background.mutate().alpha = aaa
                fake_status_bar.background.mutate().alpha = aaa
                mRgRecord1.visibility = View.GONE
                tvTitle.visibility = View.GONE
            } else {
                rlView.background.mutate().alpha = 255
                fake_status_bar.background.mutate().alpha = 255
                mRgRecord1.visibility = View.VISIBLE
                tvTitle.visibility = View.VISIBLE
            }
        }
    }

    private fun initData() {
        mPresenter.healthClassDetail(HealthClassDetailReq(intent.getIntExtra("id", -1)))
        mPresenter.healthClassDetailMusic(HealthClassDetailMusicReq(intent.getIntExtra("id", -1), page))

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
                            runOnUiThread {
                                tvNameadsada.text = source.music_title
                                ivImg1.loadImage(source.music_img)
                                startSeek()
                            }
                            healthClassMusicFragment.setSelect(source.music_id)
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
            ivImg1.loadImage(AudioPlayer.getInstance().getNowPlaying()!!.music_img)
            tvNameadsada.text = AudioPlayer.getInstance().getNowPlaying()!!.music_title
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
