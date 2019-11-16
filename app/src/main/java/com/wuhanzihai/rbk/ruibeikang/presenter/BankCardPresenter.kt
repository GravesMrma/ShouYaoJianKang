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

class BankCardPresenter @Inject constructor() : BasePresenter<BankCardView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl


    fun myBankCard() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.myBankCard()
                .excute(object : BaseSubscriber<BankCardBean>(mView) {
                    override fun onNext(t: BankCardBean) {
                        super.onNext(t)
                        mView.onBankCardResult(t)
                    }
                }, lifecycleProvider)
    }

    fun addBankCard(req:AddBankCardReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.addBankCard(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onAddBankCardResult()
                    }
                }, lifecycleProvider)
    }

    fun deleteBankCard(req:DeleteBankCardReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.deleteBankCard(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onAddBankCardResult()
                    }
                }, lifecycleProvider)
    }
}