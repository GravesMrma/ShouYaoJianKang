package com.wuhanzihai.rbk.ruibeikang.presenter

import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
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
}