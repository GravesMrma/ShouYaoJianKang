package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UserInfoReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SetSexPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.act
import org.jetbrains.anko.toast

class AuthActivity : BaseMvpActivity<SetSexPresenter>(), SetSexView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onSaveInfoResult() {
        showTextDesc(act, "实名认证保存成功"){
            finish()
        }
    }

    override fun onUserInfoResult(result: LoginData) {
        edName.setText(result.rel_name)
        edCard.setText(result.rel_code)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvText.onClick {
            if (edName.text.isEmpty() || edCard.text.isEmpty()) {
                toast("请完善信息")
                return@onClick
            }
            mPresenter.saveInfo(UserInfoReq("",
                    "",
                    "",
                    "",
                    edName.text.toString(),
                    edCard.text.toString(),
                    "",
                    ""))
        }
    }

    private fun initData() {
        mPresenter.getUserInfo()
    }
}
