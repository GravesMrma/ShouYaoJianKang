package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LoginView
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GetCodeReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class LoginPresenter @Inject constructor() : BasePresenter<LoginView>()  {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun sendCode(getCodeReq: GetCodeReq){
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.sendCode(getCodeReq)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onGetCode()
                    }
                }, lifecycleProvider)
    }

    fun login(loginReq: LoginReq){
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.login(loginReq)
                .excute(object : BaseSubscriber<LoginData>(mView) {
                    override fun onNext(t: LoginData) {
                        super.onNext(t)
                        mView.onLoginResult(t)
                    }
                }, lifecycleProvider)
    }
}