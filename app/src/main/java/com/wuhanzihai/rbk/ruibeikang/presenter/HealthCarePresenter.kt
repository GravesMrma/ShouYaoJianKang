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

class HealthCarePresenter @Inject constructor() : BasePresenter<HealthCareView>() {

    @Inject
    lateinit var mallServiceImpl: MallServiceImpl

    fun goodClass() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.goodClass()
                .excute(object : BaseSubscriber<MutableList<GoodsClassBean>>(mView) {
                    override fun onNext(t: MutableList<GoodsClassBean>) {
                        super.onNext(t)
                        mView.onGoodsClassResult(t)
                    }
                }, lifecycleProvider)
    }

    fun getGoodsList(req:GoodsReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.getGoodsList(req)
                .excute(object : BaseSubscriber<GoodsResult>(mView) {
                    override fun onNext(t:GoodsResult) {
                        super.onNext(t)
                        mView.onGoodsListResult(t)
                    }
                }, lifecycleProvider)
    }

    fun getGoodsClassList(req:GoodsClassListReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.getGoodsClassList(req)
                .excute(object : BaseSubscriber<MutableList<Child>>(mView) {
                    override fun onNext(t:MutableList<Child>) {
                        super.onNext(t)
                        mView.onGoodsListClassResult(t)
                    }
                }, lifecycleProvider)
    }
}