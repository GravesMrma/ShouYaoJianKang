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
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthHabitsBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthHabitsBundle
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthHabitsDetailReq
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthHabitsDetailFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthHabitsDetailSportFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthHabitsPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthHabitsView
import kotlinx.android.synthetic.main.activity_health_habits_detail.*
import org.jetbrains.anko.act
import java.util.*

class HealthHabitsDetailActivity : BaseMvpActivity<HealthHabitsPresenter>(), HealthHabitsView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onHealthHabitsDetailResult(result: MutableList<HealthHabitsBean>) {

        listClass.addAll(result)
        adapterClass.notifyDataSetChanged()
        if (listClass.isNotEmpty()){
            listClass.first().isCheck = true
        }
        for (i in 0 until result.size) {
            var fragment = HealthHabitsDetailFragment()
            var bundle = Bundle()
            bundle.putInt("cate_id", result[i].cat_id)
            fragment.arguments = bundle
            mStack.add(fragment)
        }

        if (mStack.isEmpty()) {
            var fragment = HealthHabitsDetailSportFragment()
            var bundle = Bundle()
            bundle.putInt("id", intent.getIntExtra("id", -1))
            fragment.arguments = bundle
            mStack.add(fragment)
        }
        pageAdapter.notifyDataSetChanged()
    }


    private lateinit var listClass: MutableList<HealthHabitsBean>
    private lateinit var adapterClass: BaseQuickAdapter<HealthHabitsBean, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_habits_detail)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        tvTitle.setTitleText(intent.getStringExtra("title"))

        initView()

        initData()
    }

    private val mStack = Stack<Fragment>()
    private lateinit var pageAdapter: FragmentPagerAdapter

    private fun initView() {
        listClass = mutableListOf()
        adapterClass = object : BaseQuickAdapter<HealthHabitsBean, BaseViewHolder>(R.layout.item_health_habits_title, listClass) {
            override fun convert(helper: BaseViewHolder?, item: HealthHabitsBean?) {
                helper!!.setText(R.id.tvText, item!!.cat_name)
                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
            }
        }
        rvClass.adapter = adapterClass
        rvClass.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)
        adapterClass.setOnItemClickListener { _, _, position ->
            if (!listClass[position].isCheck) {
                listClass.forEach {
                    it.isCheck = false
                }
                listClass[position].isCheck = true
                adapterClass.notifyDataSetChanged()
                vpView.currentItem = position
            }
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
                listClass.forEach {
                    it.isCheck = false
                }
                listClass[p0].isCheck = true
                adapterClass.notifyDataSetChanged()
            }
        })
    }

    private fun initData() {
        mPresenter.healthHabitsDetailList(HealthHabitsDetailReq(intent.getIntExtra("id", -1)))
    }
}
