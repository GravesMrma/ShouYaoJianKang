package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.StoreDateBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.StoreTimeBean
import kotlinx.android.synthetic.main.activity_store_appoint_detail.*
import org.jetbrains.anko.act

class StoreAppointDetailActivity : AppCompatActivity() {

    private lateinit var list: MutableList<StoreDateBean>
    private lateinit var adapter: BaseQuickAdapter<StoreDateBean,BaseViewHolder>

    private lateinit var list1: MutableList<StoreTimeBean>
    private lateinit var adapter1: BaseQuickAdapter<StoreTimeBean,BaseViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_appoint_detail)

        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        adapter = object :BaseQuickAdapter<StoreDateBean,BaseViewHolder>(R.layout.item_store_day,list){
            override fun convert(helper: BaseViewHolder?, item: StoreDateBean?) {

            }
        }
        rvDay.adapter = adapter
        rvDay.layoutManager = GridLayoutManager(act,1,RecyclerView.HORIZONTAL,false)
        adapter.setOnItemClickListener { _, _, position ->
            list1.clear()
            list1.addAll(list[position].list)
            adapter1.notifyDataSetChanged()
        }

        list1 = mutableListOf()
        adapter1 = object :BaseQuickAdapter<StoreTimeBean,BaseViewHolder>(R.layout.item_store_time,list1){
            override fun convert(helper: BaseViewHolder?, item: StoreTimeBean?) {

            }
        }
        rvTime.adapter = adapter1
        rvTime.layoutManager = GridLayoutManager(act,4)
    }

    private fun initData(){

    }
}
