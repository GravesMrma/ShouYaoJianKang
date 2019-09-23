package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.fragment.QuestionFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.QuestionResultFragment
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem11_11_1
import kotlinx.android.synthetic.main.activity_question.*
import org.jetbrains.anko.act
import java.util.*

class QuestionActivity : AppCompatActivity() {

    private val mStack = Stack<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        initView()
        initData()
    }

    private fun initView() {
        mStack.add(QuestionFragment())
        mStack.add(QuestionResultFragment())

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
                    0 -> mRgRecord.check(R.id.mRbOne)
                    1 -> mRgRecord.check(R.id.mRbTwo)
                }
            }
        })

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mRbOne -> {
                    vpView.currentItem = 0
                }
                R.id.mRbTwo -> {
                    vpView.currentItem = 1
                }
            }
        }
    }

    private fun initData() {


    }
}
