package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
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
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.AfterSaleActivity
import com.wuhanzihai.rbk.ruibeikang.activity.LogisticsActivity
import com.wuhanzihai.rbk.ruibeikang.activity.OrderDetailActivity
import com.wuhanzihai.rbk.ruibeikang.activity.PayActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.Goodslist
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.OrderReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.*
import com.wuhanzihai.rbk.ruibeikang.presenter.OrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.OrderView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import per.goweii.anylayer.AnyLayer

@SuppressLint("ValidFragment")
class OrderFragment(var status: Int) : BaseMvpFragment<OrderPresenter>(), OrderView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onOrderResult(result: OrderBean) {
        srView.finishRefresh()
        srView.finishLoadMore()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<OrderItem>
    private lateinit var adapter: BaseQuickAdapter<OrderItem, BaseViewHolder>
    private lateinit var dividerItemFourteen: DividerItemOrderItem
    private var page = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
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
                when (item.status) {
                    1 -> {
                        helper.setText(R.id.tvState, "待付款")
                        helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.orange))
                        helper.getView<TextView>(R.id.tvCommit).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.tvMore).visibility = View.GONE
                        helper.setText(R.id.tvCommit, "去支付")
                        helper.setTextColor(R.id.tvCommit, ContextCompat.getColor(act, R.color.white))
                        helper.getView<TextView>(R.id.tvCommit).setBackgroundResource(R.drawable.sp_orange_14)
                        helper.getView<TextView>(R.id.tvCommit).onClick {
                            startActivity<PayActivity>("data" to list[helper.layoutPosition].order_id.toString()
                                    , "price" to list[helper.layoutPosition].sub_total.toDouble())
                        }
                    }
                    2 -> {
                        helper.setText(R.id.tvState, "待发货")
                        helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.orange))
                        helper.getView<TextView>(R.id.tvCommit).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.tvMore).visibility = View.GONE
                        helper.setText(R.id.tvCommit, "提醒发货")
                        helper.setTextColor(R.id.tvCommit, ContextCompat.getColor(act, R.color.gray_99))
                        helper.getView<TextView>(R.id.tvCommit).setBackgroundResource(R.drawable.sp_gray_14_stk)
                        helper.getView<TextView>(R.id.tvCommit).onClick {
                            toast("提醒发货")
                        }
                    }
                    3 -> {
                        helper.setText(R.id.tvState, "待收货")
                        helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.orange))
                        helper.getView<TextView>(R.id.tvCommit).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.tvMore).visibility = View.VISIBLE
                        helper.setText(R.id.tvMore, "查看物流")
                        helper.setTextColor(R.id.tvMore, ContextCompat.getColor(act, R.color.gray_99))
                        helper.getView<TextView>(R.id.tvMore).setBackgroundResource(R.drawable.sp_gray_14_stk)
                        helper.getView<TextView>(R.id.tvMore).onClick {
                            toast("查看物流")
                        }
                        helper.setText(R.id.tvCommit, "确认收货")
                        helper.setTextColor(R.id.tvCommit, ContextCompat.getColor(act, R.color.white))
                        helper.getView<TextView>(R.id.tvCommit).setBackgroundResource(R.drawable.sp_orange_14)
                        helper.getView<TextView>(R.id.tvCommit).onClick {
                            toast("确认收货")
                        }
                    }
                    4 -> {
                        helper.setText(R.id.tvState, "已发货")
                        helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.orange))
                        helper.getView<TextView>(R.id.tvCommit).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.tvMore).visibility = View.VISIBLE
                        helper.setText(R.id.tvMore, "查看物流")
                        helper.setTextColor(R.id.tvMore, ContextCompat.getColor(act, R.color.gray_99))
                        helper.getView<TextView>(R.id.tvMore).setBackgroundResource(R.drawable.sp_gray_14_stk)
                        helper.getView<TextView>(R.id.tvMore).onClick {
                            startActivity<LogisticsActivity>("orderId" to item.order_id,
                                    "storeId" to item.store_id)
                        }
                        helper.setText(R.id.tvCommit, "确认收货")
                        helper.setTextColor(R.id.tvCommit, ContextCompat.getColor(act, R.color.white))
                        helper.getView<TextView>(R.id.tvCommit).setBackgroundResource(R.drawable.sp_orange_14)
                        helper.getView<TextView>(R.id.tvCommit).onClick {
                            toast("确认收货")
                        }
                    }
                    5 -> {
                        helper.setText(R.id.tvState, "已确认收货")
                    }
                    6 -> {
                        helper.setText(R.id.tvState, "交易完成")
                        helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.orange))
                        helper.getView<TextView>(R.id.tvCommit).visibility = View.VISIBLE
                        helper.getView<TextView>(R.id.tvMore).visibility = View.VISIBLE
                        helper.setText(R.id.tvMore, "查看物流")
                        helper.setTextColor(R.id.tvMore, ContextCompat.getColor(act, R.color.gray_99))
                        helper.getView<TextView>(R.id.tvMore).setBackgroundResource(R.drawable.sp_gray_14_stk)
                        helper.getView<TextView>(R.id.tvMore).onClick {
                            toast("查看物流")
                        }
                        helper.setText(R.id.tvCommit, "再次购买")
                        helper.setTextColor(R.id.tvCommit, ContextCompat.getColor(act, R.color.white))
                        helper.getView<TextView>(R.id.tvCommit).setBackgroundResource(R.drawable.sp_orange_14)
                        helper.getView<TextView>(R.id.tvCommit).onClick {
                            toast("再次购买")
                        }
                    }
                }

                helper.getView<RecyclerView>(R.id.rvView).setOnTouchListener { v, event ->
                    return@setOnTouchListener helper.itemView.onTouchEvent(event)
                }

                helper.getView<RecyclerView>(R.id.rvView).run {
                    adapter = object : BaseQuickAdapter<Goodslist, BaseViewHolder>(R.layout.item_order_goods, item.goodslist) {
                        override fun convert(helper: BaseViewHolder?, item: Goodslist?) {
                            if (item!!.pro_good_remark.image.contains(",")) {
                                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.pro_good_remark.image.substring(0, item.pro_good_remark.image.indexOf(",")))
                            } else {
                                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.pro_good_remark.image)
                            }
                            helper.setText(R.id.tvName, item.pro_good_remark.name)
                                    .setText(R.id.tvSpec, item.pro_good_remark.skuname)
                                    .setText(R.id.tvPrice, item.pro_good_remark.price)
                                    .setText(R.id.tvNum, "x${item.pro_num}")
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
        adapter.emptyView = getEmptyView(act, R.mipmap.empty_order, "暂无订单")
//        adapter.emptyView = layoutInflater.inflate(R.layout.empty_order_view, null)
        adapter.setOnItemClickListener { _, _, position ->
            startActivity<OrderDetailActivity>("data" to list[position])
        }
        srView.setOnRefreshListener {
            list.clear()
            page = 1
            initData()
        }
        srView.setOnLoadMoreListener {
            page++
            initData()
        }
    }

    private fun initData() {
        mPresenter.getOrder(OrderReq(0, status, "", page))
    }
}