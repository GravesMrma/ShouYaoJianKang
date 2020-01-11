package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.data.entity.Goodslist
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CloseOrderReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamOrderIdReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.OrderReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemOrderDetailItem
import com.wuhanzihai.rbk.ruibeikang.presenter.OrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.OrderView
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import kotlinx.android.synthetic.main.activity_order_detail.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnyLayer
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailActivity : BaseMvpActivity<OrderPresenter>(), OrderView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    private val dialog by lazy {
        val anyLayer = AnyLayer.with(act)
                .contentView(R.layout.layout_cancel_order)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
                .onClick(R.id.tvCancel) { anyLayer, v ->
                    anyLayer.dismiss()
                }
                .onClick(R.id.tvSure) { anyLayer, v ->
                    startActivity<PayActivity>("data" to orderId.toString()
                            , "price" to price)
                    anyLayer.dismiss()
                }
        anyLayer
    }

    override fun onOrderResult(result: OrderBean) {
        if (result.totle == 1) {
            refresh(result.item.first())
        }
    }

    override fun onOrderClose() {
        setResult(4321)
        finish()
    }

    override fun onOrderSure() {
        initData()
    }


    private lateinit var list: MutableList<Goodslist>
    private lateinit var adapter: BaseQuickAdapter<Goodslist, BaseViewHolder>
    private var orderId = 0
    private var price = 0.0

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

    private lateinit var order_no: String
    private fun initData() {
        order_no = intent.getStringExtra("orderNo")
        mPresenter.getOrder(OrderReq(0, 0, order_no, 1))
    }

    private fun refresh(data: OrderItem) {
        orderId = data.order_id
        price = data.sub_total.toDouble()
        tvName.text = data.address_user
        tvPhone.text = data.address_tel
        tvAddress.text = data.address

        tvOrderSn.text = data.order_no
        tvOrderTime.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(data.add_time.toLong() * 1000))
        tvOrderQB.text = "完成支付才能获取健康币哦！"
        tvOrderPay.text = "支付宝支付"
        tvOrderWay.text = "快递配送"
        tvOrderMoeny.text = "¥" + data.total
        tvMoney.text = data.total
        tvOrderKdMoeny.text = (data.total.toDouble() - data.sub_total.toDouble()).toString()

        when (data.status) {
            // 待支付
            1 -> {
                clBg.setBackgroundColor(ContextCompat.getColor(act, R.color.orderbg1))
                ivState.setImageResource(R.mipmap.ic_stateorder3)
                tvText.text = "请尽快完成支付!"
                index = data.order_paid_expire - (System.currentTimeMillis()/ 1000).toInt()
                countTime()
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "取消订单"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeft.onClick {
                    CustomSinglePicker(act){
                        mPresenter.closeOrder(CloseOrderReq(orderId,it))
                    }.setData(mutableListOf("商品选错了","信息填写错误,重新购买","暂时不想购买","商品断货","其他原因")).setIsLoop(false).show()
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_orange_14)
                tvOrderRight.text = "去支付"
                tvOrderRight.setTextColor(ContextCompat.getColor(act, R.color.white))
                tvOrderRight.onClick {
                    startActivity<PayActivity>("data" to data.order_id.toString()
                            , "price" to data.sub_total.toDouble())
                }
            }
            // 商家待发货
            2 -> {
                clBg.setBackgroundColor(ContextCompat.getColor(act, R.color.green_08))
                ivState.setImageResource(R.mipmap.ic_stateorder1)
                tvText.text = "厂商待发货"
                tvText1.text = "预计在24小时之内发货"
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "申请退款"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeft.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderRight.text = "提醒发货"
                tvOrderRight.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderRight.onClick {
                    toast("提醒发货")
                }
            }
            // 商家待发货
            3 -> {
                clBg.setBackgroundColor(ContextCompat.getColor(act, R.color.green_08))
                ivState.setImageResource(R.mipmap.ic_stateorder1)
                tvText.text = "厂商待发货"
                tvText1.text = "预计在24小时之内发货"
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "申请退款"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeft.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderRight.text = "提醒发货"
                tvOrderRight.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderRight.onClick {
                    toast("提醒发货")
                }
            }
            4 -> {
                clBg.setBackgroundColor(ContextCompat.getColor(act, R.color.green_08))
                tvText.text = "厂商已发货"
                ivState.setImageResource(R.mipmap.ic_stateorder4)
                tvText1.text = "剩余6天23小时自动确认"
                tvOrderLeftP.visibility = View.VISIBLE
                tvOrderLeftP.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeftP.text = "申请售后"
                tvOrderLeftP.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeftP.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "查看物流"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeft.onClick {
                    startActivity<LogisticsActivity>("orderId" to data.order_id,
                            "storeId" to data.store_id)
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_orange_14)
                tvOrderRight.text = "确认收货"
                tvOrderRight.setTextColor(ContextCompat.getColor(act, R.color.white))
                tvOrderRight.onClick {
                    showChoseText(act,"你是否确认收货?","确认收货后订单状态将更改为已完成，商品问题可申请售后"
                    ,"确认收货"){
                        mPresenter.sureOrder(NoParamOrderIdReq(orderId))
                    }
                }
            }
            5 -> {
                clBg.setBackgroundColor(ContextCompat.getColor(act, R.color.green_08))
                tvText.text = "已收货"
                tvText1.text = "已收货"
                ivState.setImageResource(R.mipmap.ic_stateorder4)
                tvOrderLeftP.visibility = View.VISIBLE
                tvOrderLeftP.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeftP.text = "申请售后"
                tvOrderLeftP.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeftP.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "查看物流"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeft.onClick {
                    startActivity<LogisticsActivity>("orderId" to data.order_id,
                            "storeId" to data.store_id)
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_orange_14)
                tvOrderRight.text = "再次购买"
                tvOrderRight.setTextColor(ContextCompat.getColor(act, R.color.white))
                tvOrderRight.onClick {
                    toast("再次购买")
                }
            }
            6 -> {
                clBg.setBackgroundColor(ContextCompat.getColor(act, R.color.green_08))
                tvText.text = "订单已完成"
                tvText1.text = "您的该笔订单已确认完成"
                ivState.setImageResource(R.mipmap.ic_stateorder2)
                tvOrderLeftP.visibility = View.VISIBLE
                tvOrderLeftP.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeftP.text = "申请售后"
                tvOrderLeftP.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeftP.onClick {
                    startActivity<StandardWebActivity>("title" to "在线客服"
                            , "data" to "http://pft.zoosnet.net/LR/Chatpre.aspx?id=PFT35316404&lng=cn")
                }
                tvOrderLeft.setBackgroundResource(R.drawable.sp_gray_14_stk)
                tvOrderLeft.text = "查看物流"
                tvOrderLeft.setTextColor(ContextCompat.getColor(act, R.color.gray_66))
                tvOrderLeft.onClick {
                    startActivity<LogisticsActivity>("orderId" to data.order_id,
                            "storeId" to data.store_id)
                }
                tvOrderRight.setBackgroundResource(R.drawable.sp_orange_14)
                tvOrderRight.text = "再次购买"
                tvOrderRight.setTextColor(ContextCompat.getColor(act, R.color.white))
                tvOrderRight.onClick {
                    toast("再次购买")
                }
            }
        }
        list.clear()
        list.addAll(data.goodslist)
        adapter.notifyDataSetChanged()
    }

    private val handler = Handler()

    private var index = 0

    private var runnable = Runnable {
        countTime()
    }

    private fun countTime(){
        index--
        tvText1.text = "还剩${MyUtils.parseTimeSeconds(index)}订单失效"
        dialog.getView<TextView>(R.id.tvContent).text = "请在 ${MyUtils.parseTimeSeconds(index)} 内完成支付，\n否则订单将会自动关闭！"
        handler.postDelayed(runnable,1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}
