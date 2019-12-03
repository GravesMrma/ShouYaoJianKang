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

class ApplyCashPresenter @Inject constructor() : BasePresenter<ApplyCashView>() {

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

    fun myCard(req:NoParamIdTypeReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.myCard(req)
                .excute(object : BaseSubscriber<MyCardBean>(mView) {
                    override fun onNext(t: MyCardBean) {
                        super.onNext(t)
                        mView.onCardResult(t)
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

    fun applyCashRecord(req:NoParamIdPageReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyCashRecord(req)
                .excute(object : BaseSubscriber<ApplyCashRecordBean>(mView) {
                    override fun onNext(t: ApplyCashRecordBean) {
                        super.onNext(t)
                        mView.onApplyCashRecordResult(t)
                    }
                }, lifecycleProvider)
    }

    fun applyCash(req:ApplyCashReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyCash(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onApplyCashResult()
                    }
                }, lifecycleProvider)
    }

    fun applyCashList(req:ApplyCashListReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyCashList(req)
                .excute(object : BaseSubscriber<ApplyCashListBean>(mView) {
                    override fun onNext(t: ApplyCashListBean) {
                        super.onNext(t)
                        mView.onApplyCashListResult(t)
                    }
                }, lifecycleProvider)
    }

    fun applyCashListDetail() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyCashListDetail()
                .excute(object : BaseSubscriber<ApplyCashListDetail>(mView) {
                    override fun onNext(t: ApplyCashListDetail) {
                        super.onNext(t)
                        mView.onApplyCashListDetail(t)
                    }
                }, lifecycleProvider)
    }

}