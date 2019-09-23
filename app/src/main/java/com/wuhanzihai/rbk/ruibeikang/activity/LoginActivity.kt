package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.LoginPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LoginView
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GetCodeReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import android.os.Build
import android.net.Uri
import com.alibaba.android.arouter.facade.annotation.Route
import com.hhjt.baselibrary.utils.LoginUtils
import com.huawei.android.hms.agent.HMSAgent
import com.huawei.android.hms.agent.push.handler.GetTokenHandler
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import org.jetbrains.anko.toast
import com.huawei.hms.support.api.push.TokenResult

@Route(path = "/rbk/ruibeikang/activity/LoginActivity")
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetCode() {
        toast("短信已发送")
    }

    override fun onLoginResult(result: LoginData) {
        toast("登录成功")
        LoginUtils.saveLoginStatus(true, result.token,result.user_id)
        GlobalBaseInfo.setBaseInfo(result)
        if (result.first_login == 1) {
            startActivity<SetSexActivity>()
        } else {
            startActivity<MainActivity>()
        }
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d("HMSconnect", "搭配上科技岛派克服静安寺")

        HMSAgent.connect(this) { rst ->
            Log.e("HMSconnect", rst.toString())
        }
        HMSAgent.Push.getToken { rst -> Log.e("HMSconnect", rst.toString()) }

        StatusBarUtil.setTranslucentForImageView(act, 0, null)

        if (LoginUtils.getLoginStatus()){
            startActivity<MainActivity>()
            finish()
        }

        GetCodeReq("13971298139")

        initView()
    }

    private fun initView() {
        tvCode.onClick {
            mPresenter.sendCode(GetCodeReq(edAccount.text.toString()))
        }

        btLogin.onClick {
            mPresenter.login(LoginReq(edAccount.text.toString(), edPwd.text.toString()))
        }
    }
}
