package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
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
import android.os.CountDownTimer
import com.alibaba.android.arouter.facade.annotation.Route
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.utils.LoginUtils
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.media.MusicService
import org.jetbrains.anko.startService
import org.jetbrains.anko.support.v4.startService
import org.jetbrains.anko.toast

//import com.huawei.hms.support.api.push.TokenResult

@Route(path = "/rbk/ruibeikang/activity/LoginActivity")
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onGetCode() {
        toast("短信已发送")
        tvCode.isSelected = true
        countDownTimer.start()
    }

    override fun onLoginResult(result: LoginData) {
        toast("登录成功")
        LoginUtils.saveLoginStatus(true, result.token, result.user_id)
        GlobalBaseInfo.setBaseInfo(result)
        if (result.first_login == 0) {
            startActivity<SetSexActivity>()
        } else {
            startActivity<MainActivity>()
        }
        finish()
    }

    private var countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
        override fun onFinish() {
            tvCode.isSelected = false
            tvCode.text = "发送验证码"
        }

        override fun onTick(p0: Long) {
            tvCode.isSelected = true
            tvCode.text = "${(p0 / 1000)}S后重发"
        }
    }

    private val api by lazy {
        WXAPIFactory.createWXAPI(this, BaseConstant.APP_WXID, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)

        if (LoginUtils.getLoginStatus()) {
            startActivity<MainActivity>()
            finish()
        } else {
            initView()

            initData()
        }
    }

    private fun initView() {
        tvCode.onClick {
            if (!tvCode.isSelected) {
                mPresenter.sendCode(GetCodeReq(edAccount.text.toString()))
            }
        }

        btLogin.onClick {
            mPresenter.login(LoginReq(edAccount.text.toString(), edPwd.text.toString()))
        }

        btWXLogin.onClick {
            var req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = "shouyaojiank"
            api.sendReq(req)
        }

        tvArg.onClick {
            startActivity<StandardWebActivity>("title" to "用户协议"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=722")
        }
    }

    private fun initData() {
        api.registerApp(BaseConstant.APP_WXID)
    }
}
