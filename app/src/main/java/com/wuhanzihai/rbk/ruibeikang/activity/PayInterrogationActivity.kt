package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import com.alipay.sdk.app.PayTask
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderIdBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderPayBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ChosePeopleReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.PayInterrogationReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.InterrogationPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.InterrogationView
import kotlinx.android.synthetic.main.activity_pay_interrogation.*
import org.jetbrains.anko.*

class PayInterrogationActivity : BaseMvpActivity<InterrogationPresenter>(), InterrogationView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreateQuestion(result: OrderIdBean) {

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

    private var order_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_interrogation)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        order_id = intent.getIntExtra("orderId",0)

        initView()

        initData()
    }

    private fun initView() {
        tvPay.onClick {
            mPresenter.payInterrogation(PayInterrogationReq(order_id))
        }
    }

    private fun initData() {

    }
}
