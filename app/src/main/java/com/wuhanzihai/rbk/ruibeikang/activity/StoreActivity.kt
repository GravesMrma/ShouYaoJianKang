package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.activity_store.*
import org.jetbrains.anko.act

class StoreActivity : AppCompatActivity() {

    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)


        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_store_goods,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,2)

    }

    private fun initData(){

    }
}
