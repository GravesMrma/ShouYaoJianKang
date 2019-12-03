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

class ApplyCardPresenter @Inject constructor() : BasePresenter<ApplyCardView>() {

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

    fun applyCard(req:ApplyCardReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyCard(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onApplyCard()
                    }
                }, lifecycleProvider)
    }

    fun applyCardRecord(req:NoParamIdDisIdPageReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.applyCardRecord(req)
                .excute(object : BaseSubscriber<CardRecord>(mView) {
                    override fun onNext(t: CardRecord) {
                        super.onNext(t)
                        mView.onApplyCardRecord(t)
                    }
                }, lifecycleProvider)
    }

}