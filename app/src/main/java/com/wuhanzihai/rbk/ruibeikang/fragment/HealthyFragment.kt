package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.AccompanyingActivity
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthTaskBean
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem12_14_12
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemHealthTask
import kotlinx.android.synthetic.main.fragment_healthy.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class HealthyFragment : Fragment() {
    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>
    private lateinit var list: MutableList<String>

    private lateinit var taskList: MutableList<HealthTaskBean>
    private lateinit var taskAdapter: BaseQuickAdapter<HealthTaskBean,BaseViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_healthy,null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView(){
        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_image,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItem12_14_12(act))
        adapter.setOnItemClickListener { adapter, view, position ->
            when(position){
                0-> startActivity<AccompanyingActivity>()
            }
        }

        taskList = mutableListOf()
        taskList.add(HealthTaskBean("",false))
        taskList.add(HealthTaskBean("",false))
        taskList.add(HealthTaskBean("",false))
        taskList.add(HealthTaskBean("",false))
        taskList.add(HealthTaskBean("",false))
        taskAdapter = object : BaseQuickAdapter<HealthTaskBean,BaseViewHolder>(R.layout.item_health_task,taskList){
            override fun convert(helper: BaseViewHolder?, item: HealthTaskBean?) {

            }
        }
        rvView1.adapter = taskAdapter
        rvView1.layoutManager = GridLayoutManager(act,1)
        rvView1.addItemDecoration(DividerItemHealthTask(act))

    }

    private fun initData(){

    }
}