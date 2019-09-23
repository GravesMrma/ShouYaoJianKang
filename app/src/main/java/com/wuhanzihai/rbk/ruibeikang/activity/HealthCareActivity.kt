package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthTitleBean
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthCareFragment
import kotlinx.android.synthetic.main.activity_health_care.*
import org.jetbrains.anko.act
import java.util.*

//健康医疗
class HealthCareActivity : AppCompatActivity() {
    private lateinit var adapter: BaseQuickAdapter<HealthTitleBean,BaseViewHolder>
    private lateinit var list: MutableList<HealthTitleBean>

    private val mStack = Stack<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_care)



//        initView()
//
//        initData()
    }

//    private fun initView(){
//        list = mutableListOf()
//
//        mStack.add(HealthCareFragment(0))
//        mStack.add(HealthCareFragment(1))
//        mStack.add(HealthCareFragment(2))
//        mStack.add(HealthCareFragment(3))
//        mStack.add(HealthCareFragment(4))
//        mStack.add(HealthCareFragment(5))
//
//        adapter = object : BaseQuickAdapter<HealthTitleBean,BaseViewHolder>(R.layout.item_health_title,list){
//            override fun convert(helper: BaseViewHolder?, item: HealthTitleBean?) {
//                helper!!.setText(R.id.tvText,item!!.cat_id)
//                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
//            }
//        }
//        rvView.adapter = adapter
//        rvView.layoutManager = GridLayoutManager(act,1,RecyclerView.HORIZONTAL,false)
//        adapter.setOnItemClickListener { _, _, position ->
//            list.forEach {
//                it.isCheck = false
//            }
//            list[position].isCheck = true
//            adapter.notifyDataSetChanged()
//            vpView.currentItem = position
//        }
//
//        val adapterv = object : FragmentPagerAdapter(supportFragmentManager) {
//            override fun getItem(position: Int): Fragment {
//                return mStack[position]
//            }
//
//            override fun getCount(): Int {
//                return mStack.size
//            }
//
//            override fun getPageTitle(position: Int): CharSequence? {
//                return null
//            }
//        }
//        vpView.adapter = adapterv
//        vpView.setNoFocus(false)
//        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(p0: Int) {}
//            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
//            override fun onPageSelected(position: Int) {
//                list.forEach {
//                    it.isCheck = false
//                }
//                list[position].isCheck = true
//                adapter.notifyDataSetChanged()
//                rvView.scrollToPosition(position)
//
//            }
//        })
//    }
//
//    private fun initData(){
//
//    }

}
