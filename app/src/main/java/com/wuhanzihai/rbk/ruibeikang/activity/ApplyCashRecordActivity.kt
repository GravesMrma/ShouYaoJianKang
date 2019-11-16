package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
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
        mStack.add(ApplyCashRecordFragment(3))
        mStack.add(ApplyCashRecordFragment(4))

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
        vpView.setNoFocus(true)
        vpView.offscreenPageLimit = 2

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mRbNor -> {
                    vpView.currentItem = 0
                }
                R.id.mRbDzz -> {
                    vpView.currentItem = 1
                }
                R.id.mRbYzz -> {
                    vpView.currentItem = 1
                }
                R.id.mRbWtg -> {
                    vpView.currentItem = 1
                }
            }
        }

    }

    private fun initData(){

    }
}