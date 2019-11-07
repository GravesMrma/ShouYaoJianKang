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
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GoodsClassListReq
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthCareFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthCarePresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthCareView
import kotlinx.android.synthetic.main.activity_health_care.*
import org.jetbrains.anko.act
import org.jetbrains.anko.support.v4.act
import java.util.*

//健康医疗 ...   等等大分类
class HealthCareActivity : BaseMvpActivity<HealthCarePresenter>(), HealthCareView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onGoodsListClassResult(result: MutableList<Child>) {
        list.addAll(result)
        if (childId == 0) {
            if (list.isNotEmpty()) {
                list.first().isCheck = true
            }
        }
        adapter.notifyDataSetChanged()
        for (bean in list) {
            mStack.add(HealthCareFragment(bean.id))
        }
        adapterv.notifyDataSetChanged()
        if (childId != 0) {
            for (i in 0 until result.size) {
                if (childId == result[i].id) {
                    list[i].isCheck = true
                    adapter.notifyDataSetChanged()
                    vpView.currentItem = i
                    rvView.scrollToPosition(i)
                }
            }
        }
    }

    private lateinit var adapter: BaseQuickAdapter<Child, BaseViewHolder>
    private lateinit var list: MutableList<Child>

    private lateinit var adapterTag: BaseQuickAdapter<ChildItem, BaseViewHolder>
    private lateinit var listTag: MutableList<ChildItem>
    private lateinit var adapterv: FragmentPagerAdapter
    private val mStack = Stack<HealthCareFragment>()
    private var fatherId = -1
    private var childId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_care)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        tvTitle.setTitleText(intent.getStringExtra("title"))
        fatherId = intent.getIntExtra("fatherId", -1)
        childId = intent.getIntExtra("childId", 0)

        initView()
        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<Child, BaseViewHolder>(R.layout.item_health_title, list) {
            override fun convert(helper: BaseViewHolder?, item: Child?) {
                helper!!.setText(R.id.tvText, item!!.name)
                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)
        adapter.setOnItemClickListener { _, _, position ->
            if (!list[position].isCheck) {
                list.forEach {
                    it.isCheck = false
                }
                list[position].isCheck = true
                adapter.notifyDataSetChanged()
                vpView.currentItem = position

                listTag.clear()
//                listTag.addAll(list[position].child)
                adapterTag.notifyDataSetChanged()
            }
        }

        listTag = mutableListOf()
        adapterTag = object : BaseQuickAdapter<ChildItem, BaseViewHolder>(R.layout.item_health_tag, listTag) {
            override fun convert(helper: BaseViewHolder?, item: ChildItem?) {
                helper!!.setText(R.id.tvText, item!!.name)
                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
            }
        }
        rvClass.adapter = adapterTag
        rvClass.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)
        adapterTag.setOnItemClickListener { adapter, view, position ->
            if (!listTag[position].isCheck) {
                for (child in listTag) {
                    child.isCheck = false
                }
                listTag[position].isCheck = !listTag[position].isCheck
                adapterTag.notifyDataSetChanged()
                for (i in 0 until list.size) {
                    if (list[i].isCheck) {
                        mStack[i].refreshData(listTag[position].id)
                    }
                }
            }
        }

        adapterv = object : FragmentPagerAdapter(supportFragmentManager) {
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
        vpView.adapter = adapterv
        vpView.setNoFocus(false)
        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(position: Int) {
                list.forEach {
                    it.isCheck = false
                }
                list[position].isCheck = true
                adapter.notifyDataSetChanged()

                if (last > position) { // 向左滑动
                    var a = position - 1
                    if (position < 0) a = 0
                    rvView.scrollToPosition(a)

                } else { // 向右滑动
                    var a = position + 1
                    if (position >= mStack.size) a = mStack.size - 1
                    rvView.scrollToPosition(a)
                }
                last = position
            }
        })
    }

    private var last = -1

    private fun initData() {
        mPresenter.getGoodsClassList(GoodsClassListReq(fatherId, childId))
    }
}
