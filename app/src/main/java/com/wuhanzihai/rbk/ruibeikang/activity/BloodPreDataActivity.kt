package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_29_14
import kotlinx.android.synthetic.main.activity_blood_pre_data.*
import org.jetbrains.anko.act

// 血压数据
class BloodPreDataActivity : AppCompatActivity() {

    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_pre_data)


        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_body_fat_data,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItem14_29_14(act))

    }

    private fun initData(){}

}
