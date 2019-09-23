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
import kotlinx.android.synthetic.main.layout_recyclerview.*
import org.jetbrains.anko.support.v4.act

class QuestionFragment:Fragment() {

    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recyclerview,null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_question,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
    }

    private fun initData(){}
}