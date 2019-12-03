package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.utils.KeyboardUtil
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.fragment.PhoneNumberFragment
import kotlinx.android.synthetic.main.activity_apply_phone.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.act
import java.util.*

class ApplyPhoneActivity : AppCompatActivity() {
    private val mStack = Stack<PhoneNumberFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_phone)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView(){
        tvTitle.setMoreTextAction {
            startActivity<StandardWebActivity>("title" to "返利规则"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=773")
        }
        mStack.add(PhoneNumberFragment(1))
        mStack.add(PhoneNumberFragment(2))

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

    private fun initData(){

    }
}
