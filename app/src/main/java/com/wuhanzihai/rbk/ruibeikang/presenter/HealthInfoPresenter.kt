package com.wuhanzihai.rbk.ruibeikang.presenter

import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class HealthInfoPresenter @Inject constructor() : BasePresenter<HealthInfoView>() {

    @Inject
    lateinit var infoServiceImpl: InfoServiceImpl

    fun healthInfoClass() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthInfoClass()
                .excute(object : BaseSubscriber<MutableList<HealthTitleBean>>(mView) {
                    override fun onNext(t: MutableList<HealthTitleBean>) {
                        super.onNext(t)
                        mView.onHealthTitleResult(t)
                    }
                }, lifecycleProvider)
    }

    fun healthFoodClass(req:IdReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthFoodClass(req)
                .excute(object : BaseSubscriber<HealthFoodBean>(mView) {
                    override fun onNext(t: HealthFoodBean) {
                        super.onNext(t)
                        mView.onHealthFoodResult(t)
                    }
                }, lifecycleProvider)
    }

    fun healthInfoList(healthListReq: HealthListReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthInfoList(healthListReq)
                .excute(object : BaseSubscriber<HealthListBean>(mView) {
                    override fun onNext(t: HealthListBean) {
                        super.onNext(t)
                        mView.onHealthListResult(t)
                    }
                }, lifecycleProvider)
    }

    fun healthInfoBanner(healthListReq: HealthBannerReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthInfoBanner(healthListReq)
                .excute(object : BaseSubscriber<HealthBannerBean>(mView) {
                    override fun onNext(t: HealthBannerBean) {
                        super.onNext(t)
                        mView.onHealthBannerResult(t)
                    }
                }, lifecycleProvider)
    }
}