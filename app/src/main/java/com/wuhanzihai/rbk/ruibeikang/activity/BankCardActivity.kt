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
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DeleteBankCardReq
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

    override fun onBankCardResult(result: BankCardBean) {
        if (result != null) {
            if (result.card_id == 0) {
                rlView1.visibility = View.VISIBLE
            } else {
                rlView2.visibility = View.VISIBLE
                tvName.text = result.bank_name
                tvType.text = result.card_type
                tvCode.text = toCardNo(result.card_number)
                tvCard.text = result.degreename
                id = result.card_id
            }
        }
    }

    override fun onAddBankCardResult() {

    }

    override fun onDelBankCardResult() {

    }

    private var id = 0

    private val  dialog by lazy {
        val anyLayer = AnyLayer.with(act)
                .contentView(R.layout.layout_chose)
                .backgroundColorRes(R.color.clarity_50)
                .gravity(Gravity.CENTER)
                .cancelableOnTouchOutside(true)
                .cancelableOnClickKeyBack(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_card)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvAdd.onClick {
            startActivity<AddBankCardActivity>()
        }

        tvReduce.onClick {

            showTextDesc()
            mPresenter.deleteBankCard(DeleteBankCardReq(id))
        }
    }

    private fun initData() {
        mPresenter.myBankCard()
    }

    private fun toCardNo(code: String): String {
        var a = code.length
        var b = 0
        b = a / 4 +1
        var list = mutableListOf<String>()
        for (i in 0 until b) {
            if (i == b-1) {
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
