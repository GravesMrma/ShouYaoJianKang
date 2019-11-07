package com.wuhanzihai.rbk.ruibeikang.activity

import android.graphics.drawable.TransitionDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.animation.AlphaAnimation
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.*
import com.wuhanzihai.rbk.ruibeikang.utils.BraceletManagerUtil
import com.wuhanzihai.rbk.ruibeikang.utils.TabLayoutUtil
import kotlinx.android.synthetic.main.activity_sport_data.*
import org.jetbrains.anko.act
import java.util.ArrayList

class SportDataActivity : AppCompatActivity() {

    private lateinit var vpAdapter: VPAdapter
    private lateinit var stepFragment: StepFragment
    private lateinit var sleepFragment: SleepFragment
    private lateinit var bloodFragment: BloodFragment
    private lateinit var heartRateFragment: HeartRateFragment
//    private lateinit var bloodPreFragment: BloodPreFragment

    private var index = 0

    val statusBarHeight: Int
        get() {
            var statusBarHeight = 0
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = resources.getDimensionPixelSize(resourceId)
            }
            return statusBarHeight
        }

    private var alphaAnimation: AlphaAnimation? = null
    private var transitionDrawable: TransitionDrawable? = null

    private var last = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport_data)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)
        index = intent.getIntExtra("index", -1)

        val linearParams = ivTool.layoutParams as ConstraintLayout.LayoutParams //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
        linearParams.height = statusBarHeight// 控件的宽强制设成30
        ivTool.layoutParams = linearParams //使设置好的布局参数应用到控件

        alphaAnimation = AlphaAnimation(0.0f, 1.0f)
        alphaAnimation!!.duration = 1000    //深浅动画持续时间
        alphaAnimation!!.fillAfter = true   //动画结束时保持结束的画面
        initView()
        initData()
    }

    fun initView() {
        findViewById<View>(R.id.ivBack).setOnClickListener { view -> finish() }
        stepFragment = StepFragment()
        sleepFragment = SleepFragment()
        bloodFragment = BloodFragment()
        heartRateFragment = HeartRateFragment()
//        bloodPreFragment = BloodPreFragment()

        vpAdapter = VPAdapter(supportFragmentManager)
        vpAdapter.addFragment(stepFragment, "步数")
        vpAdapter.addFragment(sleepFragment, "睡眠")
        vpAdapter.addFragment(bloodFragment, "血氧")
        vpAdapter.addFragment(heartRateFragment, "心率")
//        vpAdapter.addFragment(bloodPreFragment, "血压")

        vpView.adapter = vpAdapter
        vpView.offscreenPageLimit = 4
        tlView.setupWithViewPager(vpView)
        tlView.post { TabLayoutUtil.setTabLine(this, tlView, 4) }
        tlView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == 0) {
                    when (last) {
                        1 -> {
                            val drawableArray1 = arrayOf(resources.getDrawable(R.drawable.sp_sleep_bg), resources.getDrawable(R.drawable.sp_step_bg))
                            transitionDrawable = TransitionDrawable(drawableArray1)
                        }
                        2 -> {
                            val drawableArray2 = arrayOf(resources.getDrawable(R.drawable.sp_heart_bg), resources.getDrawable(R.drawable.sp_step_bg))
                            transitionDrawable = TransitionDrawable(drawableArray2)
                        }
                        3 -> {
                            val drawableArray3 = arrayOf(resources.getDrawable(R.drawable.sp_blood_bg), resources.getDrawable(R.drawable.sp_step_bg))
                            transitionDrawable = TransitionDrawable(drawableArray3)
                        }
                        4 -> {
                            val drawableArray4 = arrayOf(resources.getDrawable(R.drawable.sp_bloodpre_bg), resources.getDrawable(R.drawable.sp_step_bg))
                            transitionDrawable = TransitionDrawable(drawableArray4)
                        }
                    }
                    ivImgBg.setImageDrawable(transitionDrawable)
                    transitionDrawable!!.startTransition(800)
                    last = 0
                }
                if (tab.position == 1) {
                    when (last) {
                        0 -> {
                            val drawableArray1 = arrayOf(resources.getDrawable(R.drawable.sp_step_bg), resources.getDrawable(R.drawable.sp_sleep_bg))
                            transitionDrawable = TransitionDrawable(drawableArray1)
                        }
                        2 -> {
                            val drawableArray2 = arrayOf(resources.getDrawable(R.drawable.sp_heart_bg), resources.getDrawable(R.drawable.sp_sleep_bg))
                            transitionDrawable = TransitionDrawable(drawableArray2)
                        }
                        3 -> {
                            val drawableArray3 = arrayOf(resources.getDrawable(R.drawable.sp_blood_bg), resources.getDrawable(R.drawable.sp_sleep_bg))
                            transitionDrawable = TransitionDrawable(drawableArray3)
                        }
                        4 -> {
                            val drawableArray4 = arrayOf(resources.getDrawable(R.drawable.sp_bloodpre_bg), resources.getDrawable(R.drawable.sp_sleep_bg))
                            transitionDrawable = TransitionDrawable(drawableArray4)
                        }
                    }
                    ivImgBg.setImageDrawable(transitionDrawable)
                    transitionDrawable!!.startTransition(800)
                    last = 1
                }
                if (tab.position == 2) {
                    when (last) {
                        0 -> {
                            val drawableArray1 = arrayOf(resources.getDrawable(R.drawable.sp_step_bg), resources.getDrawable(R.drawable.sp_blood_bg))
                            transitionDrawable = TransitionDrawable(drawableArray1)
                        }
                        1 -> {
                            val drawableArray2 = arrayOf(resources.getDrawable(R.drawable.sp_sleep_bg), resources.getDrawable(R.drawable.sp_blood_bg))
                            transitionDrawable = TransitionDrawable(drawableArray2)
                        }
                        3 -> {
                            val drawableArray3 = arrayOf(resources.getDrawable(R.drawable.sp_heart_bg), resources.getDrawable(R.drawable.sp_blood_bg))
                            transitionDrawable = TransitionDrawable(drawableArray3)
                        }
                        4 -> {
                            val drawableArray4 = arrayOf(resources.getDrawable(R.drawable.sp_bloodpre_bg), resources.getDrawable(R.drawable.sp_blood_bg))
                            transitionDrawable = TransitionDrawable(drawableArray4)
                        }
                    }
                    ivImgBg.setImageDrawable(transitionDrawable)
                    transitionDrawable!!.startTransition(800)
                    last = 2
                }
                if (tab.position == 3) {
                    when (last) {
                        0 -> {
                            val drawableArray1 = arrayOf(resources.getDrawable(R.drawable.sp_step_bg), resources.getDrawable(R.drawable.sp_heart_bg))
                            transitionDrawable = TransitionDrawable(drawableArray1)
                        }
                        1 -> {
                            val drawableArray2 = arrayOf(resources.getDrawable(R.drawable.sp_sleep_bg), resources.getDrawable(R.drawable.sp_heart_bg))
                            transitionDrawable = TransitionDrawable(drawableArray2)
                        }
                        2 -> {
                            val drawableArray3 = arrayOf(resources.getDrawable(R.drawable.sp_blood_bg), resources.getDrawable(R.drawable.sp_heart_bg))
                            transitionDrawable = TransitionDrawable(drawableArray3)
                        }
                        4 -> {
                            val drawableArray4 = arrayOf(resources.getDrawable(R.drawable.sp_bloodpre_bg), resources.getDrawable(R.drawable.sp_heart_bg))
                            transitionDrawable = TransitionDrawable(drawableArray4)
                        }
                    }
                    ivImgBg.setImageDrawable(transitionDrawable)
                    transitionDrawable!!.startTransition(800)
                    last = 3
                }
                if (tab.position == 4) {
                    when (last) {
                        0 -> {
                            val drawableArray1 = arrayOf(resources.getDrawable(R.drawable.sp_step_bg), resources.getDrawable(R.drawable.sp_bloodpre_bg))
                            transitionDrawable = TransitionDrawable(drawableArray1)
                        }
                        1 -> {
                            val drawableArray2 = arrayOf(resources.getDrawable(R.drawable.sp_sleep_bg), resources.getDrawable(R.drawable.sp_bloodpre_bg))
                            transitionDrawable = TransitionDrawable(drawableArray2)
                        }
                        2 -> {
                            val drawableArray3 = arrayOf(resources.getDrawable(R.drawable.sp_blood_bg), resources.getDrawable(R.drawable.sp_bloodpre_bg))
                            transitionDrawable = TransitionDrawable(drawableArray3)
                        }
                        3 -> {
                            val drawableArray4 = arrayOf(resources.getDrawable(R.drawable.sp_heart_bg), resources.getDrawable(R.drawable.sp_bloodpre_bg))
                            transitionDrawable = TransitionDrawable(drawableArray4)
                        }
                    }
                    ivImgBg.setImageDrawable(transitionDrawable)
                    transitionDrawable!!.startTransition(800)
                    last = 4
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    fun initData() {
        if (index != -1) {
            vpView.currentItem = index
        }
        BraceletManagerUtil.instance.getCombinationData()
    }

    private class VPAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val fragmentList = ArrayList<Fragment>()
        private val titleList = ArrayList<String>()

        fun addFragment(fragment: Fragment, title: CharSequence) {
            fragmentList.add(fragment)
            titleList.add(title.toString())
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}
