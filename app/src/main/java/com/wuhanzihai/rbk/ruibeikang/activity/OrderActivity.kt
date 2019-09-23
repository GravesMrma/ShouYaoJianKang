package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.OrderFragment
import kotlinx.android.synthetic.main.activity_order.*
import java.util.*

class OrderActivity : AppCompatActivity() {
    private val mStack = Stack<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
        initData()
    }

    private fun initView() {
        mStack.add(OrderFragment())
        mStack.add(OrderFragment())
        mStack.add(OrderFragment())
        mStack.add(OrderFragment())
        mStack.add(OrderFragment())

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
                    0 -> mRgRecord.check(R.id.mRbInfo)
                    1 -> mRgRecord.check(R.id.mRbDfk)
                    2 -> mRgRecord.check(R.id.mRbDfh)
                    3 -> mRgRecord.check(R.id.mRbDsh)
                    4 -> mRgRecord.check(R.id.mRbDpj)
                }
            }
        })

        mRgRecord.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.mRbInfo -> {
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
                R.id.mRbDpj -> {
                    vpView.currentItem = 4
                }
            }
        }
    }

    private fun initData() {


    }

}
