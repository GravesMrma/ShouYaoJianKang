package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LoginView
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MallBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderPayBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MallView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.PayView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class PayPresenter @Inject constructor() : BasePresenter<PayView>() {

    @Inject
    lateinit var mallServiceImpl: MallServiceImpl


    fun payOrder(req: PayOrderReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.payOrder(req)
                .excute(object : BaseSubscriber<OrderPayBean>(mView) {
                    override fun onNext(t: OrderPayBean) {
                        super.onNext(t)
                        mView.onPayResult(t)
                    }
                }, lifecycleProvider)
    }
}