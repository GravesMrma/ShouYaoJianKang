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

        tvText11.text = Html.fromHtml("卡号库存是由平台分配给--<font color=#FFA200>守护者、全球合伙人</font> 两个代理商角色的<font color=#FFA200>高级别卡号</font>，刚激活的尊享会员无卡号库存！")
        tvText22.text = Html.fromHtml("拥有卡号库存，您可以享受更高的售卡返佣金额，举例：<font color=#FFA200>尊享会员</font>分享链接让好友购买1张APP激活卡号<font color=#FFA200>可赚-->80元</font>，而拥有卡号库存的<font color=#FFA200>守护者可赚-->121元，全球合伙人可赚-->141元</font>")
        tvText33.text = Html.fromHtml("您想获取卡号库存，首先要向平台申请成为--><font color=#FFA200>守护者</font>  或  <font color=#FFA200>全球合伙人</font>，点击下方等级申请按钮，填写相关资料提交审核，平台会在24小时内与您取得联系；")
        tvText44.text = Html.fromHtml("您分享好友<font color=#FFA200>支付299元购买会员卡号</font>并<font color=#FFA200>激活APP后</font>，您的卡号库存便会<font color=#FFA200>扣除1个库存</font>，同时售卡佣金也会返现到您的可提现金额中；如果卡号库存用尽，您可以再次向平台提交申请进行购买，否则此时分享好友购买只能获取初级的80元售卡佣金，不能获取对应您等级的佣金（<font color=#FFA200>守护者</font>售卡佣金：<font color=#FFA200>121/张 </font>、 <font color=#FFA200>合伙人</font>售卡佣金：<font color=#FFA200>141/张</font>）")
    }

    private var data: RebateBean? = null
    private fun initData() {
        mPresenter.applyLevelInfo()
        data = Hawk.get<RebateBean>(BaseConstant.REBATE_INFO)
        dialogTop.getView<TextView>(R.id.mRgRecord).text = "默认申请 ${data!!.applycardnumber} 张"
        dialogTop.getView<TextView>(R.id.tvDent).text = "您现在的等级为:  ${data!!.g_name}"
        if (data!!.dg_id != 0) {
            tvCommit.text = "申请卡号库存"
        }
        tvNumber.text = data!!.stock.toString()
    }
}
