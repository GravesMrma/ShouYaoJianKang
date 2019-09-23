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
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.GoodsDetailView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MallView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var mallServiceImpl: MallServiceImpl

    fun goodsDetail(goodsDetailReq: GoodsDetailReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.goodsDetail(goodsDetailReq)
                .excute(object : BaseSubscriber<GoodsDetailBean>(mView) {
                    override fun onNext(t: GoodsDetailBean) {
                        super.onNext(t)
                        mView.onGoodsDetailResult(t)
                    }
                }, lifecycleProvider)
    }


    fun addCart(req: AddCartReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.addCart(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onAddCartResult()
                    }
                }, lifecycleProvider)
    }

}