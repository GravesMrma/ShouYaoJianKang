package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_set_tag.*
import org.jetbrains.anko.act

class SetTagActivity : AppCompatActivity() {

    private lateinit var list: MutableList<String>
//    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>
    private lateinit var adapter: TagAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_tag)


        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        list.add("健康人群")
        list.add("儿童人群")
        list.add("老年人群")
        list.add("孕妇人群")
        list.add("高血糖")
        list.add("高血脂")
        list.add("高血压人群")
        adapter = object : TagAdapter<String>(list) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        rvView.adapter = adapter
    }

    private fun initData(){

    }
}
