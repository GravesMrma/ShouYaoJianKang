package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.RecycleViewFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.WebFragment
import kotlinx.android.synthetic.main.activity_health_call.*
import java.util.*

class HealthCallActivity : AppCompatActivity() {

    private val mStack = Stack<Fragment>()
    private lateinit var webFragment: WebFragment
    private lateinit var recycleViewFragment: RecycleViewFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_call)

        initView()
    }

    private fun initView(){

        webFragment = WebFragment()
        recycleViewFragment = RecycleViewFragment()
        mStack.add(webFragment)
        mStack.add(recycleViewFragment)

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

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.mRbInfo->{
                    vpView.currentItem = 0
                }
                R.id.mRbCall->{
//                    vpView.currentItem = 1
                }
            }

        }
    }

    private fun initData(){}
}
