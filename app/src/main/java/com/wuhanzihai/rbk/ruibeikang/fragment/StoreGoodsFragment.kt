package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.AfterSaleActivity
import com.wuhanzihai.rbk.ruibeikang.activity.LogisticsActivity
import com.wuhanzihai.rbk.ruibeikang.activity.OrderDetailActivity
import com.wuhanzihai.rbk.ruibeikang.activity.PayActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.Goodslist
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamOrderIdReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.OrderReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.*
import com.wuhanzihai.rbk.ruibeikang.presenter.OrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.OrderView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import per.goweii.anylayer.AnyLayer

@SuppressLint("ValidFragment")
class StoreGoodsFragment(var status: Int) : BaseMvpFragment<OrderPresenter>(), OrderView {
    override fun injectComponent() {
//        DaggerUserComponent.builder().activityComponent(mActivityComponent)
//                .userModule(UserModule()).build().inject(this)
//        mPresenter.mView = this
    }

    override fun onOrderResult(result: OrderBean) {
        srView.finish()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    override fun onOrderDelete() {
        page = 1
        list.clear()
        initData()
    }

    private lateinit var list: MutableList<OrderItem>
    private lateinit var adapter: BaseQuickAdapter<OrderItem, BaseViewHolder>
    private lateinit var dividerItemFourteen: DividerItemOrderItem
    private var page = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_store_goods, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        dividerItemFourteen = DividerItemOrderItem(act)

        adapter = object : BaseQuickAdapter<OrderItem, BaseViewHolder>(R.layout.item_order, list) {
            override fun convert(helper: BaseViewHolder?, item: OrderItem?) {
                helper!!.setText(R.id.tvOrder, "订单编号:${item!!.order_no}")

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem12_10_12(act))
        adapter.emptyView = getEmptyView(act, R.mipmap.empty_order, "暂无订单")
//        adapter.emptyView = layoutInflater.inflate(R.layout.empty_order_view, null)
        adapter.setOnItemClickListener { _, _, position ->
            startActivityForResult<OrderDetailActivity>(1234,"orderNo" to list[position].order_no)
        }
        srView.refresh({
            list.clear()
            page = 1
            initData()
        },{
            page++
            initData()
        })

    }

    private fun initData() {
        mPresenter.getOrder(OrderReq(0, status, "", page))
    }

}