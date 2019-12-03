package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
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
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class RebatePresenter @Inject constructor() : BasePresenter<RebateView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun disbutorIndex() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.disbutorIndex()
                .excute(object : BaseSubscriber<RebateBean>(mView) {
                    override fun onNext(t: RebateBean) {
                        super.onNext(t)
                        mView.onRebateResult(t)
                    }
                }, lifecycleProvider)
    }

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

    fun applyCashDetail() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyCashDetail()
                .excute(object : BaseSubscriber<CashDetailBean>(mView) {
                    override fun onNext(t: CashDetailBean) {
                        super.onNext(t)
                        mView.onCashDetailResult(t)
                    }
                }, lifecycleProvider)
    }
}