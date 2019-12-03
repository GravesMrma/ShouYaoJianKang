package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateLevelBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ApplyCardReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCardPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCardView
import kotlinx.android.synthetic.main.activity_apply_card_detail.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivityForResult

class ApplyCardDetailActivity : BaseMvpActivity<ApplyCardPresenter>(), ApplyCardView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onApplyCard() {

    }

    override fun onLevelResult(result: MutableList<RebateLevelBean>) {

    }

    private var adrId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_card_detail)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))


        initView()

        initData()
    }

    private fun initView(){
        rlView1.onClick {
            if (rlView1.isSelected){
                return@onClick
            }
            rlView1.isSelected = true
            tvNumber1.isSelected = true
            tvNumber11.isSelected = true
            tvMoney1.isSelected = true
            tvMoney11.isSelected = true
            rlView2.isSelected = false
            tvNumber2.isSelected = false
            tvNumber22.isSelected = false
            tvMoney2.isSelected = false
            tvMoney22.isSelected = false
            rlView3.isSelected = false
            tvNumber3.isSelected = false
            tvNumber33.isSelected = false
            tvMoney3.isSelected = false
            tvMoney33.isSelected = false
        }

        rlView2.onClick {
            if (rlView2.isSelected){
                return@onClick
            }
            rlView1.isSelected = false
            tvNumber1.isSelected = false
            tvNumber11.isSelected = false
            tvMoney1.isSelected = false
            tvMoney11.isSelected = false
            rlView2.isSelected = true
            tvNumber2.isSelected = true
            tvNumber22.isSelected = true
            tvMoney2.isSelected = true
            tvMoney22.isSelected = true
            rlView3.isSelected = false
            tvNumber3.isSelected = false
            tvNumber33.isSelected = false
            tvMoney3.isSelected = false
            tvMoney33.isSelected = false
        }

        rlView3.onClick {
            if (rlView3.isSelected){
                return@onClick
            }
            rlView1.isSelected = false
            tvNumber1.isSelected = false
            tvNumber11.isSelected = false
            tvMoney1.isSelected = false
            tvMoney11.isSelected = false
            rlView2.isSelected = false
            tvNumber2.isSelected = false
            tvNumber22.isSelected = false
            tvMoney2.isSelected = false
            tvMoney22.isSelected = false
            rlView3.isSelected = true
            tvNumber3.isSelected = true
            tvNumber33.isSelected = true
            tvMoney3.isSelected = true
            tvMoney33.isSelected = true
        }
        tvAddAddress.onClick {
            startActivityForResult<AddressActivity>(2222, "chose" to true
                    , "addId" to adrId)
        }

        clAddress.onClick {
            startActivityForResult<AddressActivity>(2222, "chose" to true
                    , "addId" to adrId)
        }
        tvCommit.onClick {
            mPresenter.applyCard(ApplyCardReq(edPhone.text.toString()
            ,edName.text.toString()
            ,2))
        }
    }

    private fun initData(){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 4444) {
            var dataAdr = data!!.getSerializableExtra("address") as AddressBean
            tvName.text = dataAdr.consignee
            tvPhone.text = dataAdr.mobile
            tvAddress.text = dataAdr.province + dataAdr.city + dataAdr.district + dataAdr.address
            adrId = dataAdr.address_id
            tvAddAddress.visibility = View.INVISIBLE
            clAddress.visibility = View.VISIBLE
            if (dataAdr.is_default == 1) {
                tvTag.visibility = View.VISIBLE
            } else {
                tvTag.visibility = View.GONE
            }
        }
    }
}
