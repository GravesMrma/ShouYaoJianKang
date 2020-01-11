package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LoginView
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.CartNumberBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MallBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GetCodeReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GoodsDetailReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UserInfoReq
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MallView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class MallPresenter @Inject constructor() : BasePresenter<MallView>() {

    @Inject
    lateinit var mallServiceImpl: MallServiceImpl

    fun mallIndex() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.mallIndex()
                .excute(object : BaseSubscriber<MallBean>(mView) {
                    override fun onNext(t: MallBean) {
                        super.onNext(t)
                        mView.onMallIndexResult(t)
                    }
                }, lifecycleProvider)
    }

    fun getCartNumber() {
        if (!checkNetWork()) {
            return
        }
//        mView.showLoading()
        mallServiceImpl.getCartNumber()
                .excute(object : BaseSubscriber<CartNumberBean>(mView) {
                    override fun onNext(t: CartNumberBean) {
                        super.onNext(t)
                        mView.onCartNumberResult(t)
                    }
                }, lifecycleProvider)
    }
}