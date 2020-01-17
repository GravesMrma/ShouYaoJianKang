package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateLevelBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ApplyCardReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCardPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCardView
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import kotlinx.android.synthetic.main.activity_card_stock.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import per.goweii.anylayer.AnyLayer

class CardStockActivity : BaseMvpActivity<ApplyCardPresenter>(), ApplyCardView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onApplyCard() {
        toast("申请成功")
    }

    override fun onLevelResult(result: MutableList<RebateLevelBean>) {
        this.result = result
//        if (result.size < 2) {
//            dialog.getView<RadioButton>(R.id.mRbGuardian).visibility = View.GONE
//            dialog.getView<View>(R.id.sView).visibility = View.GONE
//        }
    }
//
//    private val dialog by lazy {
//        val anyLayer = AnyLayer.with(act)
//                .contentView(R.layout.layout_apply_card)
//                .gravity(Gravity.CENTER)
//                .backgroundResource(R.color.clarity_40)
//                .onClick(R.id.ivClose) { anyLayer, v ->
//                    anyLayer.dismiss()
//                }
//                .onClick(R.id.tvSure) { anyLayer, v ->
//                    mPresenter.applyCard(ApplyCardReq(anyLayer.getView<EditText>(R.id.edName).text.toString(),
//                            anyLayer.getView<EditText>(R.id.edPhone).text.toString(), gId))
//                    anyLayer.dismiss()
//                }
//        anyLayer
//    }

    private val dialogTop by lazy {
        val anyLayer = AnyLayer.with(act)
                .contentView(R.layout.layout_apply_card_top)
                .gravity(Gravity.CENTER)
                .backgroundResource(R.color.clarity_40)
                .onClick(R.id.ivClose) { anyLayer, v ->
                    anyLayer.dismiss()
                }
                .onClick(R.id.tvNumber) { anyLayer, v ->
                    CustomSinglePicker(act){
                        anyLayer.getView<TextView>(R.id.tvNumber).text = it
                    }.setData(numbers).setIsLoop(false).show()
                }
                .onClick(R.id.tvSure) { anyLayer, v ->
                    mPresenter.applyCard(ApplyCardReq(anyLayer.getView<EditText>(R.id.edName).text.toString(),
                            anyLayer.getView<EditText>(R.id.edPhone).text.toString(),
                            Hawk.get<RebateBean>(BaseConstant.REBATE_INFO).dg_id))
                    anyLayer.dismiss()
                }
        anyLayer
    }

    private var gId = 0
    private lateinit var result: MutableList<RebateLevelBean>

    private var numbers = mutableListOf("200", "190", "180", "170", "160", "150", "140", "130", "120", "110", "100", "90", "80", "70", "60", "50", "40", "30", "20", "10")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_stock)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
//        dialog.getView<RadioGroup>(R.id.mRgRecord).setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.mRbGuardian -> gId = result[0].g_id
//                R.id.mRbPartner -> gId = result[1].g_id
//            }
//        }
        tvCommit.onClick {
            if (data!!.dg_id == 0) {
//                dialog.show()
                startActivity<ApplyLevelActivity>()
            } else {
                dialogTop.show()
            }
        }
        tvRecord.onClick {
            startActivity<CardStockRecordActivity>()
        }

        //  <font color=#FFA200>  </font>
        //
        //
        tvText11.text = Html.fromHtml("<font color=#FFA200>礼包库存</font>是由首要健康平台收到货款后，分配给--健康推广大使的 礼包货物库存，<font color=#FFA200>与您交付的货款一 一对应！</font>")
        tvText22.text = Html.fromHtml("您分享好友支付<font color=#FFA200>299元购买首要健康会员</font>后，您的礼包库存数量便会<font color=#FFA200>扣减1个</font>，同时售卡佣金299元会返现到您的--><font color=#FFA200>可提现金额中</font>，如果<font color=#FFA200>礼包库存用尽</font>，您可以再次向平台申请购买，<font color=#FFA200>否则</font>此时分享好友购买会员<font color=#FFA200>只能获得80元佣金！</font>")
    }

    private var data: RebateBean? = null
    private fun initData() {
        mPresenter.applyLevelInfo()
        data = Hawk.get<RebateBean>(BaseConstant.REBATE_INFO)
        dialogTop.getView<TextView>(R.id.mRgRecord).text = "默认申请 ${data!!.applycardnumber} 张"
        dialogTop.getView<TextView>(R.id.tvDent).text = "您现在的等级为:  ${data!!.g_name}"
        if (data!!.dg_id != 0) {
            tvCommit.text = "申请礼包库存"
        }
        tvNumber.text = data!!.stock.toString()
    }
}
