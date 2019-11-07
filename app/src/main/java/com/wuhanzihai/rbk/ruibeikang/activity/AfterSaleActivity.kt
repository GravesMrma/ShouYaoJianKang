package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem12_10_12
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemOrderItem
import kotlinx.android.synthetic.main.activity_after_sale.*
import org.jetbrains.anko.act

//  退款/售后
class AfterSaleActivity : AppCompatActivity() {
    private lateinit var list: MutableList<OrderBean>
    private lateinit var adapter: BaseQuickAdapter<OrderBean, BaseViewHolder>
    private lateinit var dividerItemFourteen: DividerItemOrderItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_sale)

        initView()

        initData()

    }

    private fun initView() {
        list = mutableListOf()
        dividerItemFourteen = DividerItemOrderItem(act)

        adapter = object : BaseQuickAdapter<OrderBean, BaseViewHolder>(R.layout.item_order, list) {
            override fun convert(helper: BaseViewHolder?, item: OrderBean?) {

//                helper!!.getView<RecyclerView>(R.id.rvView).run {
//                    adapter = object : BaseQuickAdapter<GoodBean, BaseViewHolder>(R.layout.item_order_goods, item!!.list) {
//                        override fun convert(helper: BaseViewHolder?, item: GoodBean?) {
//
//
//                        }
//                    }
//
//                    layoutManager = GridLayoutManager(act, 1)
//                    if (itemDecorationCount != 0) {
//                        removeItemDecoration(dividerItemFourteen)
//                    }
//                    addItemDecoration(dividerItemFourteen)
//                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem12_10_12(act))
    }

    private fun initData() {}

}
