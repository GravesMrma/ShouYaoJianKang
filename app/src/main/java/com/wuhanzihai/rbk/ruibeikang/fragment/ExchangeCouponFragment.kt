package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.AfterSaleActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.CouponBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsCouponBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CouponIdReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CouponReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdIdReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.*
import com.wuhanzihai.rbk.ruibeikang.presenter.CouponPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.CouponView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import java.text.SimpleDateFormat
import java.util.*

class ExchangeCouponFragment() : BaseMvpFragment<CouponPresenter>(), CouponView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onExchangeCouponResult(result: MutableList<GoodsCouponBean>) {
        srView.finish()
        list.addAll(result)
        adapter.notifyDataSetChanged()
    }

    override fun onTakeCouponResult() {
        page = 1
        list.clear()
        initData()
        showTextDesc(act,"兑换成功")
    }

    private lateinit var list: MutableList<GoodsCouponBean>
    private lateinit var adapter: BaseQuickAdapter<GoodsCouponBean, BaseViewHolder>
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
        adapter = object : BaseQuickAdapter<GoodsCouponBean, BaseViewHolder>(R.layout.item_coupon, list) {
            override fun convert(helper: BaseViewHolder?, item: GoodsCouponBean?) {
                helper!!.setText(R.id.tvText, item!!.name)
                        .setText(R.id.tvTime, "${SimpleDateFormat("yyyy.MM.dd").format(Date(item.start_time.toLong() * 1000))}-${SimpleDateFormat("yyyy.MM.dd").format(Date(item.end_time.toLong() * 1000))}")
                        .setText(R.id.tvDesc, item.description)
//1:代金券|2:满减券|3:折扣券|5:免单券
                var llView1 = helper.getView<LinearLayout>(R.id.llView1)
                var llView2 = helper.getView<LinearLayout>(R.id.llView2)
                var llView3 = helper.getView<LinearLayout>(R.id.llView3)
                var llView4 = helper.getView<LinearLayout>(R.id.llView4)
                llView1.visibility = View.GONE
                llView2.visibility = View.GONE
                llView3.visibility = View.GONE
                llView4.visibility = View.GONE
                when (item.type) {
                    1 -> {
                        llView1.visibility = View.VISIBLE
                        helper.setText(R.id.tvCash, item.face_money.replace(".00",""))
                    }
                    2 -> {
                        llView2.visibility = View.VISIBLE
                        helper.setText(R.id.tvCoupon, item.face_money.replace(".00",""))
                                .setText(R.id.tvLimit, "满${item.limit_money.replace(".00","")}减${item.face_money.replace(".00","")}")
                    }
                    3 -> {
                        llView3.visibility = View.VISIBLE
                        helper.setText(R.id.tvDiscount, item.face_money.replace(".00",""))
                    }
                    4 -> {
                        llView4.visibility = View.VISIBLE
//                        helper.setText(R.id.tvCoupon, item.face_money)
                    }
                }

                if (item.most_have > item.usercouponcount){
                    helper.setText(R.id.tvState, "立即兑换")
                    helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvL1tag1, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvCash, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvL1tag11, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvL2tag1, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvCoupon, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvLimit, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvDiscount, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvL3tag1, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvL3tag11, ContextCompat.getColor(act, R.color.orange))
                            .setTextColor(R.id.tvL4tag1, ContextCompat.getColor(act, R.color.orange))

                    helper.setBackgroundRes(R.id.rlView,R.mipmap.ic_coupon_bg)
                    helper.setOnClickListener(R.id.tvState) {
                        mPresenter.takeExchangeCoupons(CouponIdReq(list[helper.layoutPosition].coupon_id))
                    }
                }else{
                    helper.setText(R.id.tvState, "已兑换")
                    helper.setTextColor(R.id.tvState, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL1tag1, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvCash, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL1tag11, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL2tag1, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvCoupon, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvLimit, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvDiscount, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL3tag1, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL3tag11, ContextCompat.getColor(act, R.color.gray_99))
                            .setTextColor(R.id.tvL4tag1, ContextCompat.getColor(act, R.color.gray_99))
                    helper.setBackgroundRes(R.id.rlView,R.mipmap.ic_coupon_bg_s)
                }

                helper.setOnClickListener(R.id.tvText1) {
                    item.isShow = !item.isShow
                    if (item.isShow) {
                        helper.getView<RelativeLayout>(R.id.rlDesc).visibility = View.VISIBLE
                    } else {
                        helper.getView<RelativeLayout>(R.id.rlDesc).visibility = View.GONE
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemCoupon(act))
        adapter.setOnItemChildClickListener { _, _, position ->

        }
        adapter.emptyView = getEmptyView(act, "暂无可用优惠券")

        srView.refresh({
            page = 1
            list.clear()
            initData()
        }, {
            page++
            initData()
        })
    }

    private fun initData() {
        mPresenter.getExchangeCoupons()
    }
}