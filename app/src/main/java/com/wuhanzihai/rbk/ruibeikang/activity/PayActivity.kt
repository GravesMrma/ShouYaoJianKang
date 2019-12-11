package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import com.alipay.sdk.app.PayTask
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderPayBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.PayOrderReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.PayPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.PayView
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        tvAli.isSelected = true

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
                mPresenter.payOrder(PayOrderReq(intent.getStringExtra("data")))
            } else {
                toast("暂时不支持微信支付")
            }
        }
    }

    private fun initData() {
        tvPrice.text = intent.getDoubleExtra("price", 0.00).toString()
        tvMoney.text = intent.getDoubleExtra("price", 0.00).toString()
    }

    override fun onBackPressed() {
//        super.onBackPressed()

        dialog.show()
    }
}
