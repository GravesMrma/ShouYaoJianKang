package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LoginView
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.AddressView
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class AddressPresenter @Inject constructor() : BasePresenter<AddressView>()  {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun addAddress(req: AddAddressReq){
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.addAddress(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onAddressResult()
                    }
                }, lifecycleProvider)
    }

    fun upAddress(req: UpdateAddressReq){
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.upAddress(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onAddressResult()
                    }
                }, lifecycleProvider)
    }

    fun getAddressList(){
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.getAddressList()
                .excute(object : BaseSubscriber<MutableList<AddressBean>>(mView) {
                    override fun onNext(t:MutableList<AddressBean>) {
                        super.onNext(t)
                        mView.onAddressListResult(t)
                    }
                }, lifecycleProvider)
    }

}