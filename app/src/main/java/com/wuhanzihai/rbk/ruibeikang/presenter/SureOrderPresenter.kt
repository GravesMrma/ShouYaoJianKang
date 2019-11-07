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

class SureOrderPresenter @Inject constructor() : BasePresenter<SureOrderView>() {

    @Inject
    lateinit var mallServiceImpl: MallServiceImpl

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    // 获取购物车信息
    fun doneCart(req: DoneCartReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.doneCart(req)
                .excute(object : BaseSubscriber<SureOrderBean>(mView) {
                    override fun onNext(t: SureOrderBean) {
                        super.onNext(t)
                        mView.onDoneCartResult(t)
                    }
                }, lifecycleProvider)
    }

    // 获取购物车信息 之后生成订单
    fun commitOrder(req: CommitOrderReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.commitOrder(req)
                .excute(object : BaseSubscriber<OrderDetailBean>(mView) {
                    override fun onNext(t: OrderDetailBean) {
                        super.onNext(t)
                        mView.onCommitOrderResult(t)
                    }
                }, lifecycleProvider)
    }

    //  获取默认地址
    fun getDefAddress() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.getDefAddress()
                .excute(object : BaseSubscriber<AddressBean>(mView) {
                    override fun onNext(t: AddressBean) {
                        super.onNext(t)
                        mView.onDefAddress(t)
                    }
                }, lifecycleProvider)
    }

    //  直接购买 --- 提交订单
    fun commitBuyGoods(req: CommitBuyGoodsReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.commitBuyGoods(req)
                .excute(object : BaseSubscriber<OrderDetailBean>(mView) {
                    override fun onNext(t: OrderDetailBean) {
                        super.onNext(t)
                        mView.onCommitOrderResult(t)
                    }
                }, lifecycleProvider)
    }

}