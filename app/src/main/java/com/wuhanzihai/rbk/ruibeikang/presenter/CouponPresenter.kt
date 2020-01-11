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
import java.io.File
import javax.inject.Inject

class CouponPresenter @Inject constructor() : BasePresenter<CouponView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun getCoupons(req: CouponReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.getCoupons(req)
                .excute(object : BaseSubscriber<MutableList<CouponBean>>(mView) {
                    override fun onNext(t: MutableList<CouponBean>) {
                        super.onNext(t)
                        mView.onCouponResult(t)
                    }
                }, lifecycleProvider)
    }

    fun takeCoupon(req: NoParamIdIdReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.takeCoupon(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onTakeCouponResult()
                    }
                }, lifecycleProvider)
    }

    fun getExchangeCoupons() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.getExchangeCoupons()
                .excute(object : BaseSubscriber<MutableList<GoodsCouponBean>>(mView) {
                    override fun onNext(t: MutableList<GoodsCouponBean>) {
                        super.onNext(t)
                        mView.onExchangeCouponResult(t)
                    }
                }, lifecycleProvider)
    }

    fun takeExchangeCoupons(req: CouponIdReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.takeExchangeCoupons(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onTakeCouponResult()
                    }
                }, lifecycleProvider)
    }
}