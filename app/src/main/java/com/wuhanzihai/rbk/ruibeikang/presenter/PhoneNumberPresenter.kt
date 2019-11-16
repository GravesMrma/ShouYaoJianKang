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

class PhoneNumberPresenter @Inject constructor() : BasePresenter<PhoneNumberView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun phoneNumberList(req: PhoneNumberReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.phoneNumberList(req)
                .excute(object : BaseSubscriber<PhoneNumberBean>(mView) {
                    override fun onNext(t: PhoneNumberBean) {
                        super.onNext(t)
                        mView.onPhoneListResult(t)
                    }
                }, lifecycleProvider)
    }

    fun myDistribution(req: PhoneNumberReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.myDistribution(req)
                .excute(object : BaseSubscriber<DistributionBean>(mView) {
                    override fun onNext(t: DistributionBean) {
                        super.onNext(t)
                        mView.onDistributionResult(t)
                    }
                }, lifecycleProvider)
    }

    fun addVipCard(req:AddVipCardReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.addVipCard(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onAddCardResult()
                    }
                }, lifecycleProvider)
    }

    fun agrApply(req:AgrApplyReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.agrApply(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onAgrApplyResult()
                    }
                }, lifecycleProvider)
    }

}