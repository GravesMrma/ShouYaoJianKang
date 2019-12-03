package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.ApplyCashRecordBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.CashDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.MyCardBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ApplyCashReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdTypeReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCashPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCashView
import kotlinx.android.synthetic.main.activity_apply_cash.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import per.goweii.anylayer.AnyLayer

class ApplyCashActivity : BaseMvpActivity<ApplyCashPresenter>(), ApplyCashView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    private val dialog by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_charge)
                        .gravity(Gravity.CENTER)
                        .backgroundResource(R.color.clarity_40)
                        .cancelableOnTouchOutside(true)
                        .onClick(R.id.tvCommit) { anyLayer, v ->
                            mPresenter.applyCash(ApplyCashReq(anyLayer.getView<EditText>(R.id.edMoney).text.toString()))
                            anyLayer.dismiss()
                        }
                        .onClick(R.id.ivClose) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
                        .onClick(R.id.tvText1) { anyLayer, v ->
                            anyLayer.getView<EditText>(R.id.edMoney).setText(result!!.agent.money)
                        }
        anyLayer
    }

    override fun onRebateResult(result: RebateBean) {
        Hawk.put(BaseConstant.REBATE_INFO, result)
        LoginUtils.saveRebateId(result.agent_id)
        tvHisMoney.text = "¥" + result.totlemoney
    }

    override fun onCardResult(result: MyCardBean) {
        bankCard = result
        if (result.bankcard.card_number != null) {
            tvBank.text = result.bankcard.card_type_name + "-" + result.bankcard.bank_card_name
        }else{
            tvBank.text = "请绑定银联借记卡"
        }
    }

    override fun onCashDetailResult(result: CashDetailBean) {
        this.result = result
        tvMoney.text = result.agent.money
        tvAuthMoney.text = "¥" + result.agent.aplliy_money
        dialog.getView<TextView>(R.id.tvMoney).text = result.agent.money
    }

    override fun onApplyCashResult() {
        showTextDesc(act, "提现申请成功")
        initData()
    }

    private var result: CashDetailBean? = null
    private var bankCard: MyCardBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_cash)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvTitle.setMoreTextAction {
            startActivity<StandardWebActivity>("title" to "返利规则"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=773")
        }
        tvBank.onClick {
            startActivity<BankCardActivity>()
        }
        tvSearch.onClick {
            startActivity<ApplyCashProcessActivity>()
        }
        tvCommit.onClick {
            if (bankCard!!.bankcard.card_number == null) {
                showChoseText(act, "请先绑定银行卡") {
                    startActivity<BankCardActivity>()
                }
                return@onClick
            }
            if (Hawk.get<RebateBean>(BaseConstant.REBATE_INFO).money.toDouble() > 0.0) {
                dialog.show()
            } else {
                showTextDesc(act, "余额不足")
            }
        }
        tvCharge.onClick {
            startActivity<StandardWebActivity>("title" to "提现规则"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=774")
        }
    }

    private fun initData() {
        mPresenter.disbutorIndex()
        mPresenter.applyCashDetail()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.myCard(NoParamIdTypeReq(3))
    }
}
