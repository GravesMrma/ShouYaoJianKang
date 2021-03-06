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

class ShoppingCartPresenter @Inject constructor() : BasePresenter<ShoppingCartView>() {

    @Inject
    lateinit var mallServiceImpl: MallServiceImpl

    fun getShoppingCartList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.shoppingCart()
                .excute(object : BaseSubscriber<MutableList<ShoppingCartBean>>(mView) {
                    override fun onNext(t: MutableList<ShoppingCartBean>) {
                        super.onNext(t)
                        mView.onShoppingCartListResult(t)
                    }
                }, lifecycleProvider)
    }

    fun doneCart(req: DoneCartReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.doneCart(req)
                .excute(object : BaseSubscriber<SureOrderBean>(mView) {
                    override fun onNext(t: SureOrderBean) {
                        super.onNext(t)
                        mView.onDoneCartResult()
                    }
                }, lifecycleProvider)
    }

    fun deleteCart(req: DoneCartReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.deleteCart(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onDeleteCartResult()
                    }
                }, lifecycleProvider)
    }

    fun doneCartNum(req: DoneCartNumReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.doneCartNum(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onDoneCartNumResult()
                    }
                }, lifecycleProvider)
    }
}