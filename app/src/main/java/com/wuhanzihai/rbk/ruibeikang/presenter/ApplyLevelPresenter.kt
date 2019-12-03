package com.wuhanzihai.rbk.ruibeikang.presenter

import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import javax.inject.Inject

class ApplyLevelPresenter @Inject constructor() : BasePresenter<ApplyLevelView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun applyLevelInfo() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyLevel()
                .excute(object : BaseSubscriber<MutableList<RebateLevelBean>>(mView) {
                    override fun onNext(t: MutableList<RebateLevelBean>) {
                        super.onNext(t)
                        mView.onLevelResult(t)
                    }
                }, lifecycleProvider)
    }

    fun applyLevel(req: ApplyLevelReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyLevelAction(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onApplyLevelResult()
                    }
                }, lifecycleProvider)
    }

    fun applyLevelRecord(req:NoParamIdDisIdPageReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyLevelRecord(req)
                .excute(object : BaseSubscriber<RebateLevelRecordBean>(mView) {
                    override fun onNext(t: RebateLevelRecordBean) {
                        super.onNext(t)
                        mView.onApplyLevelRecordResult(t)
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
}