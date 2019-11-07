package com.wuhanzihai.rbk.ruibeikang.fragment

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
import com.wuhanzihai.rbk.ruibeikang.activity.AfterSaleActivity
import com.wuhanzihai.rbk.ruibeikang.activity.AppointTravelActivity
import com.wuhanzihai.rbk.ruibeikang.itemDiv.*
import kotlinx.android.synthetic.main.layout_recyclerview.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class TravelFragment : Fragment() {
    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recyclerview, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()

    }

    private fun initView() {
        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_order, list) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper!!.getView<TextView>(R.id.tvCommit).setOnClickListener {
                    startActivity<AfterSaleActivity>()
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem12_10_12(act))
        adapter.setOnItemClickListener { adapter, view, position ->
            startActivity<AppointTravelActivity>()
        }
    }

    private fun initData() {

    }
}