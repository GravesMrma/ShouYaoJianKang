package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.CouponFragment
import kotlinx.android.synthetic.main.activity_coupon.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import java.util.*

class CouponActivity : AppCompatActivity() {
    private val mStack = Stack<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        tvTitle.setMoreTextAction {
            startActivity<ExchangeCouponActivity>()
        }

        initView()
        initData()
    }

    private fun initView() {
        mStack.add(CouponFragment(0))
        mStack.add(CouponFragment(1))
        mStack.add(CouponFragment(2))
        mStack.add(CouponFragment(3))

        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
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
        vpView.setNoFocus(false)
        vpView.offscreenPageLimit = 3
        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                when (p0) {
                    0 -> mRgRecord.check(R.id.mRbAll)
                    1 -> mRgRecord.check(R.id.mRbNor)
                    2 -> mRgRecord.check(R.id.mRbUsed)
                    3 -> mRgRecord.check(R.id.mRbOver)
                }
            }
        })

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mRbAll -> {
                    vpView.currentItem = 0
                }
                R.id.mRbNor -> {
                    vpView.currentItem = 1
                }
                R.id.mRbUsed -> {
                    vpView.currentItem = 2
                }
                R.id.mRbOver -> {
                    vpView.currentItem = 3
                }
            }
        }
    }

    private fun initData() {


    }
}
