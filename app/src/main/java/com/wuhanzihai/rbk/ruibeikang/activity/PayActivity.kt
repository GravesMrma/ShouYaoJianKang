package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import com.alipay.sdk.app.PayTask
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderPayBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.PayOrderReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.PayPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.PayView
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_pay.*
import org.jetbrains.anko.*
import per.goweii.anylayer.AnyLayer

class PayActivity : BaseMvpActivity<PayPresenter>(), PayView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onPayResult(result: OrderPayBean) {
        if (intent.getDoubleExtra("price", 0.00) == 0.0){
            toast("支付成功")
            startActivity<PayResultActivity>()
            finish()
        }else{
            if(tvAli.isSelected){
                doAsync {
                    val resultMap: Map<String, String> = PayTask(act).payV2(result.order_string, true)
                    Log.e("支付信息", resultMap.toString())
                    uiThread {
                        if (resultMap["resultStatus"].equals("9000")) {
                            toast("支付成功")
                            startActivity<PayResultActivity>()
                            finish()
                        } else {
                            toast("支付失败${resultMap["memo"]}")
                        }
                    }
                }
            }
            if(tvWeChat.isSelected){
                var request =  PayReq()
                request.appId = result.appid
                request.nonceStr= result.noncestr
                request.packageValue = result.`package`
                request.partnerId = result.partnerid
                request.prepayId= result.prepayid
                request.sign= result.sign
                request.timeStamp= result.timestamp
                api.sendReq(request)
                finish()
            }
        }
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
                    startActivity<OrderActivity>()
                    finish()
                }
                .onClick(R.id.tvSure) { anyLayer, v ->
                    anyLayer.dismiss()
                }
        anyLayer
    }


    private val api by lazy {
        val api = WXAPIFactory.createWXAPI(act,null)
//        api.registerApp(BaseConstant.APP_WXID)
        api
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        tvWeChat.isSelected = true

        initView()

        initData()
    }

    private fun initView() {
        tvTitle.setBackListener {
            dialog.show()

        }
        tvWeChat.onClick {
            tvWeChat.isSelected = true
            tvAli.isSelected = false
        }

        tvAli.onClick {
            tvAli.isSelected = true
            tvWeChat.isSelected = false
        }

        tvPay.onClick {
            if (tvAli.isSelected) {
                if (intent.getDoubleExtra("price", 0.00) == 0.0){
                    mPresenter.payOrder(PayOrderReq(intent.getStringExtra("data"),"userpay"))
                }else{
                    mPresenter.payOrder(PayOrderReq(intent.getStringExtra("data"),"alipay"))
                }
            }
            if (tvWeChat.isSelected) {
                if (intent.getDoubleExtra("price", 0.00) == 0.0){
                    mPresenter.payOrder(PayOrderReq(intent.getStringExtra("data"),"userpay"))
                }else{
                    mPresenter.payOrder(PayOrderReq(intent.getStringExtra("data"),"wxpay"))
                }
            }
        }
    }

    private fun initData() {
        tvPrice.text = intent.getDoubleExtra("price", 0.00).toString()
        tvMoney.text = intent.getDoubleExtra("price", 0.00).toString()
        index = 3600
        countTime()
    }

    override fun onBackPressed() {
        dialog.show()
    }

    private val handler = Handler()

    private var index = 0

    private var runnable = Runnable {
        countTime()
    }

    private fun countTime(){
        index--
        tvTime.text = "请在${MyUtils.parseTimeSeconds(index)}内完成支付"
        dialog.getView<TextView>(R.id.tvContent).text = "请在${MyUtils.parseTimeSeconds(index)}内完成支付，\n否则订单将会自动关闭！"
        handler.postDelayed(runnable,1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}
