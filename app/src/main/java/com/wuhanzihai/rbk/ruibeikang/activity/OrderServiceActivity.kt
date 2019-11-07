package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.OrderServiceFragment
import kotlinx.android.synthetic.main.activity_order_service.*
import org.jetbrains.anko.act
import java.util.*

class OrderServiceActivity : AppCompatActivity() {
    private val mStack = Stack<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_service)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))
        initView()
        initData()

    }

    private fun initView() {
        mStack.add(OrderServiceFragment(0))
        mStack.add(OrderServiceFragment(1))
        mStack.add(OrderServiceFragment(2))
        mStack.add(OrderServiceFragment(5))

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
        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                when (p0) {
                    0 -> mRgRecord.check(R.id.mRbAll)
                    1 -> mRgRecord.check(R.id.mRbDfk)
                    2 -> mRgRecord.check(R.id.mRbDfh)
                    3 -> mRgRecord.check(R.id.mRbDsh)
                }
            }
        })

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mRbAll -> {
                    vpView.currentItem = 0
                }
                R.id.mRbDfk -> {
                    vpView.currentItem = 1
                }
                R.id.mRbDfh -> {
                    vpView.currentItem = 2
                }
                R.id.mRbDsh -> {
                    vpView.currentItem = 3
                }
            }
        }
    }

    private fun initData() {


    }
}
