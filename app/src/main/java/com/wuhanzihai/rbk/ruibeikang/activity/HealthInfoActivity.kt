package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthTitleBean
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthInfoFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthInfoPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthInfoView
import kotlinx.android.synthetic.main.activity_health_info.*
import org.jetbrains.anko.act
import java.util.*

// 健康知识
class HealthInfoActivity : BaseMvpActivity<HealthInfoPresenter>(), HealthInfoView {

    private val mStack = Stack<Fragment>()

    private lateinit var adapter: BaseQuickAdapter<HealthTitleBean, BaseViewHolder>
    private lateinit var list: MutableList<HealthTitleBean>

    private lateinit var pageAdapter: FragmentPagerAdapter


    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthTitleResult(result: MutableList<HealthTitleBean>) {
        list.addAll(result)
        list.first().isCheck = true
        adapter.notifyDataSetChanged()

        for (i in 0 until result.size) {
            var fragment = HealthInfoFragment()
            var bundle = Bundle()
            bundle.putInt("cate_id", result[i].cat_id)
            fragment.arguments = bundle
            mStack.add(fragment)
        }
        pageAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_info)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()
        initData()
    }

    private fun initView() {

        list = mutableListOf()

        adapter = object : BaseQuickAdapter<HealthTitleBean, BaseViewHolder>(R.layout.item_health_title, list) {
            override fun convert(helper: BaseViewHolder?, item: HealthTitleBean?) {
                helper!!.setText(R.id.tvText, item!!.cat_name)
                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)
        adapter.setOnItemClickListener { _, _, position ->
            list.forEach {
                it.isCheck = false
            }
            list[position].isCheck = true
            adapter.notifyDataSetChanged()
            vpView.currentItem = position
        }

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
                list.forEach {
                    it.isCheck = false
                }
                list[p0].isCheck = true
                adapter.notifyDataSetChanged()
                if (last > p0) { // 向左滑动
                    var a = p0 - 1
                    if (p0 < 0) a = 0
                    rvView.scrollToPosition(a)

                } else { // 向右滑动
                    var a = p0 + 1
                    if (p0 >= mStack.size) a = mStack.size - 1
                    rvView.scrollToPosition(a)
                }
                last = p0
            }
        })
    }

    private var last = -1

    private fun initData() {
        mPresenter.healthInfoClass()
    }
}
