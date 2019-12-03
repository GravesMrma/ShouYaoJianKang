package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LoginView
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GetCodeReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GoodsDetailReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UserInfoReq
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MallView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MineView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class MinePresenter @Inject constructor() : BasePresenter<MineView>() {

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

    fun mineIndex() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.mineIndex()
                .excute(object : BaseSubscriber<MineBean>(mView) {
                    override fun onNext(t: MineBean) {
                        super.onNext(t)
                        mView.onMineResult(t)
                    }
                }, lifecycleProvider)
    }

    fun userAdv() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.userAdv()
                .excute(object : BaseSubscriber<MineAdv>(mView) {
                    override fun onNext(t: MineAdv) {
                        super.onNext(t)
                        mView.onMineAdvResult(t)
                    }
                }, lifecycleProvider)
    }

    fun mineBanner() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.mineBanner()
                .excute(object : BaseSubscriber<Banner>(mView) {
                    override fun onNext(t: Banner) {
                        super.onNext(t)
                        mView.onMineBanner(t)
                    }
                }, lifecycleProvider)
    }

    fun isRebate() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.isRebate()
                .excute(object : BaseSubscriber<IsRebateBean>(mView) {
                    override fun onNext(t: IsRebateBean) {
                        super.onNext(t)
                        mView.onIsRebateResult(t)
                    }
                }, lifecycleProvider)
    }
}