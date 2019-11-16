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

class RebateAddressPresenter @Inject constructor() : BasePresenter<RebateAddressView>() {

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

    fun rebateAddress(req:RebateAddressReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.rebateAddress(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onRebateAddressResult()
                    }
                }, lifecycleProvider)
    }
}