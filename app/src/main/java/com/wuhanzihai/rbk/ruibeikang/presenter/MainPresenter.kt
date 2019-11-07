package com.wuhanzihai.rbk.ruibeikang.presenter

import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.VersionBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ActivationReq
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MainView
import com.wuhanzihai.rbk.ruibeikang.service.impl.IndexServiceImpl
import javax.inject.Inject

class MainPresenter @Inject constructor() : BasePresenter<MainView>() {

    @Inject
    lateinit var indexServiceImpl: IndexServiceImpl

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun getUserInfo() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.getUserInfo()
                .excute(object : BaseSubscriber<LoginData>(mView) {
                    override fun onNext(t: LoginData) {
                        super.onNext(t)
                       mView.onUserInfoResult(t)
                    }
                }, lifecycleProvider)
    }

    fun activation(req: ActivationReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.activation(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onActivationResult(t)
                    }
                }, lifecycleProvider)
    }

    fun getVersion() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.getVersion()
                .excute(object : BaseSubscriber<VersionBean>(mView) {
                    override fun onNext(t: VersionBean) {
                        super.onNext(t)
                        mView.onVersionResult(t)
                    }
                }, lifecycleProvider)
    }
}