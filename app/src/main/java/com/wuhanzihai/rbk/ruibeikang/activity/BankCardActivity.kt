package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.BankCardBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.MyCardBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DeleteBankCardReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdTypeReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.BankCardPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.BankCardView
import kotlinx.android.synthetic.main.activity_bank_card.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import per.goweii.anylayer.AnyLayer

class BankCardActivity : BaseMvpActivity<BankCardPresenter>(), BankCardView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onBankCardResult(result: MyCardBean) {
        if (result != null) {
            if (result.bankcard.card_number == null) {
                rlView1.visibility = View.VISIBLE
                rlView2.visibility = View.GONE
            } else {
                rlView1.visibility = View.GONE
                rlView2.visibility = View.VISIBLE
                tvName.text = result.bankcard.bank_name
                tvType.text = result.bankcard.card_type_name
                tvCode.text = toCardNo(result.bankcard.card_number)
                id = result.bankcard.card_id
            }
        }
        tvCard.text = result.uaer_agent_grade.g_name
    }

    override fun onDelBankCardResult() {
        showTextDesc(act, "删除成功")
        initData()
    }

    private var id = 0

    private val dialog by lazy {
        val anyLayer = AnyLayer.with(act)
                .contentView(R.layout.layout_chose)
                .gravity(Gravity.CENTER)
                .backgroundResource(R.color.clarity_40)
                .onClick(R.id.tvCancel) { anyLayer, v ->
                    anyLayer.dismiss()
                }
                .onClick(R.id.tvSure) { anyLayer, v ->
                    mPresenter.deleteBankCard(DeleteBankCardReq(id))
                    anyLayer.dismiss()
                }
        anyLayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_card)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

    }

    private fun initView() {
        tvAdd.onClick {
            startActivity<AddBankCardActivity>()
        }

        tvReduce.onClick {
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        mPresenter.myCard(NoParamIdTypeReq(0))
    }

    private fun toCardNo(code: String): String {
        var a = code.length
        var b = 0
        b = a / 4 + 1
        var list = mutableListOf<String>()
        for (i in 0 until b) {
            if (i == b - 1) {
                list.add(code.substring(i * 4))
            } else {
                list.add(code.substring(i * 4, i * 4 + 4))
            }
        }
        var result = ""
        for (s in list) {
            result = result + s + " "
        }
        return result
    }
}
