package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.Goodslist
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.OrderReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemOrderDetailItem
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemOrderItem
import com.wuhanzihai.rbk.ruibeikang.presenter.OrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.OrderView
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : BaseMvpActivity<OrderPresenter>(), OrderView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onOrderResult(result: OrderBean) {

    }

    private lateinit var list: MutableList<Goodslist>
    private lateinit var adapter: BaseQuickAdapter<Goodslist, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<Goodslist, BaseViewHolder>(R.layout.item_order_service_goods, list) {
            override fun convert(helper: BaseViewHolder?, item: Goodslist?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.pro_good_remark.image)
                helper.setText(R.id.tvName, item.pro_good_remark.name)
                        .setText(R.id.tvSpec, item.pro_good_remark.skuname)
                        .setText(R.id.tvPrice, item.pro_good_remark.price)
                        .setText(R.id.tvNum, "x${item.pro_num}")
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemOrderDetailItem(act))
    }

    private fun initData() {
        var data = intent.getSerializableExtra("data") as OrderItem
        tvName.text = data.address_user
        tvPhone.text = data.address_tel
        tvAddress.text = data.address

        tvOrderSn.text = data.order_no
        tvOrderTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(data.add_time.toLong()))
        tvOrderQB.text = "完成支付才能获取健康币哦！"
        tvOrderPay.text = "支付宝支付"
        tvOrderWay.text = "快递配送"
        tvOrderMoeny.text = data.total
        tvMoney.text = data.total
        tvOrderKdMoeny.text = (data.total.toDouble() - data.sub_total.toDouble()).toString()

        when(data.status){
            1->{
                clBg.setBackgroundColor(ContextCompat.getColor(act,R.color.orderbg1))
                tvText.text = "请尽快完成支付!"
                tvText1.text = "还剩23小时59分订单失效"
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "取消订单"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeft.onClick {
                    toast("取消订单")
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_orange_14)
                tvOrderRight.text = "去支付"
                tvOrderRight.setTextColor(ContextCompat.getColor(act,R.color.white))
                tvOrderRight.onClick {
                    startActivity<PayActivity>("data" to data.order_id.toString()
                            , "price" to data.sub_total.toDouble())
                }
            }
            2->{
                clBg.setBackgroundColor(ContextCompat.getColor(act,R.color.green_08))
                tvText.text = "商家待发货"
                tvText1.text = "预计在48小时之内发货"

                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "申请退款"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeft.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderRight.text = "提醒发货"
                tvOrderRight.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderRight.onClick {
                    toast("提醒发货")
                }
            }
            3->{
                clBg.setBackgroundColor(ContextCompat.getColor(act,R.color.green_08))
                tvText.text = "商家配货中"
                tvText1.text = "商家配货中"

                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "申请退款"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeft.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderRight.text = "提醒发货"
                tvOrderRight.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderRight.onClick {
                    toast("提醒发货")
                }
            }
            4->{
                clBg.setBackgroundColor(ContextCompat.getColor(act,R.color.green_08))
                tvText.text = "商家已发货"
                tvText1.text = "剩余6天23小时自动确认"
                tvOrderLeftP.visibility = View.VISIBLE
                tvOrderLeftP.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeftP.text = "申请售后"
                tvOrderLeftP.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeftP.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "查看物流"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeft.onClick {
                    toast("查看物流")
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_orange_14)
                tvOrderRight.text = "确认收货"
                tvOrderRight.setTextColor(ContextCompat.getColor(act,R.color.white))
                tvOrderRight.onClick {
                    toast("确认收货")
                }
            }
            5->{
                clBg.setBackgroundColor(ContextCompat.getColor(act,R.color.green_08))
                tvText.text = "已收货"
                tvText1.text = "已收货"
                tvOrderLeftP.visibility = View.VISIBLE
                tvOrderLeftP.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeftP.text = "申请售后"
                tvOrderLeftP.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeftP.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "查看物流"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeft.onClick {
                    toast("查看物流")
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_orange_14)
                tvOrderRight.text = "再次购买"
                tvOrderRight.setTextColor(ContextCompat.getColor(act,R.color.white))
                tvOrderRight.onClick {
                    toast("再次购买")
                }
            }
            6->{
                clBg.setBackgroundColor(ContextCompat.getColor(act,R.color.green_08))
                tvText.text = "订单已完成"
                tvText1.text = "您的该笔订单已确认完成"
                tvOrderLeftP.visibility = View.VISIBLE
                tvOrderLeftP.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeftP.text = "申请售后"
                tvOrderLeftP.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeftP.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "查看物流"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act,R.color.gray_66))
                tvOrderLeft.onClick {
                    toast("查看物流")
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_orange_14)
                tvOrderRight.text = "再次购买"
                tvOrderRight.setTextColor(ContextCompat.getColor(act,R.color.white))
                tvOrderRight.onClick {
                    toast("再次购买")
                }
            }
        }
        list.addAll(data.goodslist)
        adapter.notifyDataSetChanged()
    }
}
