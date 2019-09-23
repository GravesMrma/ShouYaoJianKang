package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface AddressView :BaseView {

    fun onAddressResult()

    fun onAddressListResult(result:MutableList<AddressBean>)

    fun onDefAddressResult(result:AddressBean)

    fun onAddressInfoResult(result:AddressBean)

    fun onUpAddressResult()

}