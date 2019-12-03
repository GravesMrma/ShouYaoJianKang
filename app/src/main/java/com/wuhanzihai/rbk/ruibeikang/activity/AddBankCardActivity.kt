package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.BankCardBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AddBankCardReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.BankCardPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.BankCardView
import kotlinx.android.synthetic.main.activity_add_bank_card.*
import org.jetbrains.anko.act
import org.jetbrains.anko.toast

class AddBankCardActivity : BaseMvpActivity<BankCardPresenter>(), BankCardView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAddBankCardResult() {
        toast("添加成功")
        setResult(4321)
        finish()
    }

    override fun onDelBankCardResult() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bank_card)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView(){
        tvCommit.onClick {
            if (edName.text.isNotEmpty()&&edCode.text.isNotEmpty()){
                mPresenter.addBankCard(AddBankCardReq(edName.text.toString(),edCode.text.toString()))
            }else{
                toast("请完善信息")
            }
        }
    }

    private fun initData(){

    }
}
