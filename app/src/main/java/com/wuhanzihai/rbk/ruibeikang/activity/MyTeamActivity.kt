package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.DirectFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.IndirectFragment
import kotlinx.android.synthetic.main.activity_my_team.*
import java.util.*

class MyTeamActivity : AppCompatActivity() {

    private val mStack = Stack<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)


        initView()

        initData()
    }

    private fun initView() {

        mStack.add(DirectFragment())
        mStack.add(IndirectFragment())

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
                R.id.mRbUsed -> {
                    vpView.currentItem = 1
                }
            }
        }

    }

    private fun initData() {

    }

}