package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class RebateRecordPresenter @Inject constructor() : BasePresenter<RebateRecordView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun rebateRecord(req:NoParamDisIdPageReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.rebateRecord(req)
                .excute(object : BaseSubscriber<RebateRecordBean>(mView) {
                    override fun onNext(t: RebateRecordBean) {
                        super.onNext(t)
                        mView.onRebateRecord(t)
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