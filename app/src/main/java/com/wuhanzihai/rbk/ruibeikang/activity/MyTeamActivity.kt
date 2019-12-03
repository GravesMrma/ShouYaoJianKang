package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.DirectFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.IndirectFragment
import kotlinx.android.synthetic.main.activity_my_team.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import java.util.*

class MyTeamActivity : AppCompatActivity() {

    private val mStack = Stack<Fragment>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvTitle.setMoreTextAction {
            startActivity<StandardWebActivity>("title" to "返利规则"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=773")
        }

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
        vpView.setNoFocus(false)
        vpView.offscreenPageLimit = 2
        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                if (p0 == 0){
                    mRgRecord.check(R.id.mRbNor)
                }
                if (p0 == 1){
                    mRgRecord.check(R.id.mRbUsed)
                }
            }
        })

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