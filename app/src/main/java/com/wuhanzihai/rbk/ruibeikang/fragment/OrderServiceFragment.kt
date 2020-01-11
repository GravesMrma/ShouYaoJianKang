package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.hhjt.baselibrary.utils.DateUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.OrderServiceDetailActivity
import com.wuhanzihai.rbk.ruibeikang.activity.PayActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.OrderReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.*
import com.wuhanzihai.rbk.ruibeikang.presenter.OrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.OrderView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.act
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

@SuppressLint("ValidFragment")
class OrderServiceFragment(var status: Int) : BaseMvpFragment<OrderPresenter>(), OrderView {
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
        adapter = object : BaseQuickAdapter<OrderItem, BaseViewHolder>(R.layout.item_order_service, list) {
            override fun convert(helper: BaseViewHolder?, item: OrderItem?) {

                helper!!.setText(R.id.tvName, item!!.order_no)
                        .setText(R.id.tvText, item.goodslist.first().pro_good_remark.name)
                        .setText(R.id.tvText1, "体检人:${item.address_user}")
                        .setText(R.id.tvText2, "下单时间: ${DateUtils.longToString(item.add_time.toLong() * 1000, "yyyy.MM.dd  HH:mm")}")
                when (item.status) {
                    1 -> {
                        helper.setText(R.id.tvState, "待付款")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.orange))
                        helper.setText(R.id.tvBtn, "去支付")
                        helper.getView<TextView>(R.id.tvBtn).setBackgroundResource(R.drawable.sp_orange_14)
                        helper.getView<TextView>(R.id.tvBtn).setOnClickListener {
                            startActivity<PayActivity>("data" to list[helper.layoutPosition].order_id.toString()
                                    , "price" to list[helper.layoutPosition].sub_total.toDouble())
                        }
                    }
                    2 -> {
                        helper.setText(R.id.tvState, "待服务")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.green_08))
                        helper.setText(R.id.tvBtn, "电话预约")
                        helper.getView<TextView>(R.id.tvBtn).setBackgroundResource(R.drawable.sp_green_14)
                    }
                    5 -> {
                        helper.setText(R.id.tvState, "退款审核")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.red))
                        helper.setText(R.id.tvBtn, "电话咨询")
                        helper.getView<TextView>(R.id.tvBtn).setBackgroundResource(R.drawable.sp_red_14)
                    }
                }

                when (helper.layoutPosition) {
                    0 -> {
                        helper.setImageResource(R.id.ivImg, R.mipmap.ic_order_s1)
                    }
                    1 -> {
                        helper.setImageResource(R.id.ivImg, R.mipmap.ic_order_s2)
                    }
                    2 -> {
                        helper.setImageResource(R.id.ivImg, R.mipmap.ic_order_s3)
                    }
                    3 -> {
                        helper.setImageResource(R.id.ivImg, R.mipmap.ic_order_s4)
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem12_10_12(act))
        adapter.emptyView = getEmptyView(act, "暂无订单哦")
        adapter.setOnItemClickListener { _, _, position ->
            startActivity<OrderServiceDetailActivity>("data" to list[position])
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
        mPresenter.getOrder(OrderReq(1, status, "", page))
    }
}