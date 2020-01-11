package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.CouponBean
import com.wuhanzihai.rbk.ruibeikang.fragment.ExchangeCouponFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.CouponPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.CouponView
import kotlinx.android.synthetic.main.activity_exchange_coupon.*
import org.jetbrains.anko.act
import java.util.*

class ExchangeCouponActivity : BaseMvpActivity<CouponPresenter>(),CouponView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCouponResult(result: MutableList<CouponBean>) {
    }

    override fun onTakeCouponResult() {
    }

    private val mStack = Stack<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_coupon)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()
        initData()
    }

    private fun initView() {
        mStack.add(ExchangeCouponFragment())
//        mStack.add(ExchangeCouponFragment(1))
//        mStack.add(ExchangeCouponFragment(2))
//        mStack.add(ExchangeCouponFragment(3))

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
        vpView.offscreenPageLimit = 3
        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                when (p0) {
                    0 -> mRgRecord.check(R.id.mRbAll)
                    1 -> mRgRecord.check(R.id.mRbNor)
                    2 -> mRgRecord.check(R.id.mRbUsed)
                    3 -> mRgRecord.check(R.id.mRbOver)
                }
            }
        })

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mRbAll -> {
                    vpView.currentItem = 0
                }
                R.id.mRbNor -> {
                    vpView.currentItem = 1
                }
                R.id.mRbUsed -> {
                    vpView.currentItem = 2
                }
                R.id.mRbOver -> {
                    vpView.currentItem = 3
                }
            }
        }
    }

    private fun initData() {


    }
}
