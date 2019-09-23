package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.fragment_health_care.*
import org.jetbrains.anko.support.v4.act

@SuppressLint("ValidFragment")
class HealthCareFragment(var postion: Int) : Fragment() {

    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>
    private lateinit var list: MutableList<String>

    private lateinit var adapterTag: BaseQuickAdapter<String, BaseViewHolder>
    private lateinit var listTag: MutableList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_health_care, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView() {
        listTag = mutableListOf()
        listTag.add("")
        listTag.add("")
        listTag.add("")
        listTag.add("")

        when(postion){
            0,3,4,5->{

            }
            1->{

            }
        }

        adapterTag = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_health_tag,listTag){
            override fun convert(helper: BaseViewHolder?, item: String?) {
                if (helper!!.layoutPosition == 1){
                    helper.getView<TextView>(R.id.tvText).isSelected = true
                }
            }
        }

        rvClass.adapter = adapterTag
        rvClass.layoutManager = GridLayoutManager(act,1,RecyclerView.HORIZONTAL,false)

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

        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_health_goods, list) {
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)


        srView.setOnRefreshListener {
            srView.finishRefresh()
        }
        srView.setOnLoadMoreListener {
            srView.finishLoadMore()
        }
    }

    private fun initData() {

    }
}