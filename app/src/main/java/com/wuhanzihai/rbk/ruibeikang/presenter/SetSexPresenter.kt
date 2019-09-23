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
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UserInfoReq
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class SetSexPresenter @Inject constructor() : BasePresenter<SetSexView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun sendCode(userInfoReq: UserInfoReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.saveInfo(userInfoReq)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onSaveInfoResult()
                    }
                }, lifecycleProvider)
    }
}