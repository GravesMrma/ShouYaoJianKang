package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateLevelBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ApplyLevelReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyLevelPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyLevelView
import kotlinx.android.synthetic.main.activity_apply_level.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ApplyLevelActivity : BaseMvpActivity<ApplyLevelPresenter>(), ApplyLevelView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onLevelResult(result: MutableList<RebateLevelBean>) {
        this.result.addAll(result)
        mRbOne.text = result[0].g_name
        mRbTwo.text = result[1].g_name

        tvNumber1.text = result[0].applycardnumber.toString()
        tvNumber2.text = result[1].applycardnumber.toString()
    }

    override fun onApplyLevelResult() {
        showTextDesc(act, "申请成功"){
            finish()
        }
    }

    override fun onUserInfoResult(result: LoginData) {
        GlobalBaseInfo.setBaseInfo(result)
        edName.setText(result.rel_name)
        edPhone.setText(result.mobile)
    }

    override fun onRebateResult(result: RebateBean) {
        Hawk.put(BaseConstant.REBATE_INFO, result)
        LoginUtils.saveRebateId(result.agent_id)
    }

    private var gId = -1
    private var result = mutableListOf<RebateLevelBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_level)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvTitle.setMoreTextAction {
            startActivity<StandardWebActivity>("title" to "晋升规则"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=804")
        }
        mRgRecord.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.mRbOne -> {
                    tvText1.isSelected = true
                    tvNumber1.isSelected = true
                    tvNumber11.isSelected = true
                    tvText2.isSelected = false
                    tvNumber2.isSelected = false
                    tvNumber22.isSelected = false
                    gId = result[0].g_id
                }
                R.id.mRbTwo -> {
                    tvText1.isSelected = false
                    tvNumber1.isSelected = false
                    tvNumber11.isSelected = false
                    tvText2.isSelected = true
                    tvNumber2.isSelected = true
                    tvNumber22.isSelected = true
                    gId = result[1].g_id
                }
            }
        }

        edName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (edName.text.isNotEmpty() && edPhone.text.isNotEmpty()) {
                    tvCommit.text = "确认申请"
                } else {
                    tvCommit.text = "申请等级"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        edPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (edName.text.isNotEmpty() && edPhone.text.isNotEmpty()) {
                    tvCommit.text = "确认申请"
                } else {
                    tvCommit.text = "申请等级"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        tvCommit.onClick {
            if (GlobalBaseInfo.getBaseInfo()!!.rel_code.isEmpty() || GlobalBaseInfo.getBaseInfo()!!.rel_code.length < 10) {
                showChoseText(act, "您还未完成实名认证！", "为了保护您的权益，请完成实名认证后再继续申请等级", "确定") {
                    startActivity<AuthActivity>()
                }
            } else {
                if (gId == -1) {
                    toast("请选择申请等级")
                    return@onClick
                }
                if (tvCommit.text.toString() == "确认申请") {
                    if (Hawk.get<RebateBean>(BaseConstant.REBATE_INFO).dg_id != 0 && Hawk.get<RebateBean>(BaseConstant.REBATE_INFO).g_pid == 0) {
                        showTextDesc(act, "您已是最高等级,无法申请!")
                    } else {
                        mPresenter.applyLevel(ApplyLevelReq(edPhone.text.toString(),
                                edName.text.toString(),
                                gId))
                    }
                } else {
                    toast("请完善信息")
                }
            }
        }
        tvRecord.onClick {
            startActivity<ApplyLevelRecordActivity>()
        }
    }

    private fun initData() {
        mPresenter.applyLevelInfo()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getUserInfo()
        mPresenter.disbutorIndex()
    }
}
