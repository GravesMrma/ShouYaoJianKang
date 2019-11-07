package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.itemDiv.*
import kotlinx.android.synthetic.main.layout_recyclerview.*
import org.jetbrains.anko.support.v4.act

class ServiceOrderFragment : Fragment() {
    private lateinit var list: MutableList<OrderBean>
    private lateinit var adapter: BaseQuickAdapter<OrderBean, BaseViewHolder>

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

        adapter = object : BaseQuickAdapter<OrderBean, BaseViewHolder>(R.layout.item_order_service, list) {
            override fun convert(helper: BaseViewHolder?, item: OrderBean?) {

                when(helper!!.layoutPosition){
                    0->{
                        helper.getView<TextView>(R.id.tvBtn).setBackgroundResource(R.drawable.sp_red_14)
                    }
                    1->{
                        helper.getView<TextView>(R.id.tvBtn).setBackgroundResource(R.drawable.sp_orange_14)
                    }
                    2->{
                        helper.getView<TextView>(R.id.tvBtn).setBackgroundResource(R.drawable.sp_green_14)
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemService(act))
    }

    private fun initData() {

    }
}