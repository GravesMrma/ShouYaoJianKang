package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.ApplyCashRecordFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.PhoneNumberFragment
import kotlinx.android.synthetic.main.activity_apply_cash_record.*
import org.jetbrains.anko.act
import java.util.*

class ApplyCashRecordActivity : AppCompatActivity() {
    private val mStack = Stack<ApplyCashRecordFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_cash_record)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView(){
        mStack.add(ApplyCashRecordFragment(1))
        mStack.add(ApplyCashRecordFragment(2))
        mStack.add(ApplyCashRecordFragment(4))
        mStack.add(ApplyCashRecordFragment(3))

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
        vpView.offscreenPageLimit = 4
        vpView.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                when(p0){
                    0->mRgRecord.check(R.id.mRbNor)
                    1->mRgRecord.check(R.id.mRbDzz)
                    2->mRgRecord.check(R.id.mRbYzz)
                    3->mRgRecord.check(R.id.mRbWtg)
                }
            }
        })

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mRbNor -> {
                    vpView.currentItem = 0
                }
                R.id.mRbDzz -> {
                    vpView.currentItem = 1
                }
                R.id.mRbYzz -> {
                    vpView.currentItem = 2
                }
                R.id.mRbWtg -> {
                    vpView.currentItem = 3
                }
            }
        }

    }

    private fun initData(){

    }
}