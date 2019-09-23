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
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.itemDiv.*
import kotlinx.android.synthetic.main.layout_recyclerview.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class OrderFragment : Fragment() {
    private lateinit var list: MutableList<OrderBean>
    private lateinit var adapter: BaseQuickAdapter<OrderBean, BaseViewHolder>
    private lateinit var dividerItemFourteen: DividerItemFourteen

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
        list.add(OrderBean(1, mutableListOf<GoodBean>(GoodBean(1, "2", "3"), GoodBean(1, "2", "3"))))
        list.add(OrderBean(2, mutableListOf<GoodBean>(GoodBean(1, "22", "32"), GoodBean(1, "22", "32"))))
        list.add(OrderBean(3, mutableListOf<GoodBean>(GoodBean(1, "21", "31"), GoodBean(1, "21", "31"))))
        list.add(OrderBean(4, mutableListOf<GoodBean>(GoodBean(1, "24", "34"), GoodBean(1, "24", "34"))))
        dividerItemFourteen = DividerItemFourteen(act)

        adapter = object : BaseQuickAdapter<OrderBean, BaseViewHolder>(R.layout.item_order, list) {
            override fun convert(helper: BaseViewHolder?, item: OrderBean?) {
                helper!!.getView<TextView>(R.id.tvCommit).setOnClickListener {
                    startActivity<AfterSaleActivity>()
                }

                helper!!.getView<RecyclerView>(R.id.rvView).run {
                    adapter = object : BaseQuickAdapter<GoodBean, BaseViewHolder>(R.layout.item_order_goods, item!!.list) {
                        override fun convert(helper: BaseViewHolder?, item: GoodBean?) {

                        }
                    }

                    layoutManager = GridLayoutManager(act, 1)
                    if (itemDecorationCount != 0) {
                        removeItemDecoration(dividerItemFourteen)
                    }
                    addItemDecoration(dividerItemFourteen)
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem12_10_12(act))
    }

    private fun initData() {}
}