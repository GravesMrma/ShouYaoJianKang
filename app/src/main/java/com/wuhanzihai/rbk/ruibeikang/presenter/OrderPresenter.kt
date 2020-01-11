package com.wuhanzihai.rbk.ruibeikang.presenter

import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import javax.inject.Inject

class OrderPresenter @Inject constructor() : BasePresenter<OrderView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl


    fun getOrder(req: OrderReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.getOrder(req)
                .excute(object : BaseSubscriber<OrderBean>(mView) {
                    override fun onNext(t: OrderBean) {
                        super.onNext(t)
                        mView.onOrderResult(t)
                    }
                }, lifecycleProvider)
    }

    fun sureOrder(req: NoParamOrderIdReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.sureOrder(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onOrderSure()
                    }
                }, lifecycleProvider)
    }

    fun deleteOrder(req: NoParamOrderIdReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.deleteOrder(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onOrderDelete()
                    }
                }, lifecycleProvider)
    }

    fun closeOrder(req: CloseOrderReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.closeOrder(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onOrderClose()
                    }
                }, lifecycleProvider)
    }
}