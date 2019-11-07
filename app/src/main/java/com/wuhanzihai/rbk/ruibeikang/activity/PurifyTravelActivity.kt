package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.TravelFragment
import kotlinx.android.synthetic.main.activity_purify_travel.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

class PurifyTravelActivity : AppCompatActivity() {

    private val mStack = Stack<Fragment>()
    private lateinit var pageAdapter: FragmentPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purify_travel)

        initView()

        initData()
    }

    private fun initView() {
        mStack.add(TravelFragment())
        mStack.add(TravelFragment())

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
                if (p0 == 0) {
                    mRgRecord.check(R.id.mRbAll)
                }
                if (p0 == 1) {
                    mRgRecord.check(R.id.mRbDfk)
                }
            }
        })

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.mRbAll) {
                vpView.currentItem = 0
            }
            if (checkedId == R.id.mRbDfk) {
                vpView.currentItem = 1
            }
        }
    }

    private fun initData() {

    }

}
