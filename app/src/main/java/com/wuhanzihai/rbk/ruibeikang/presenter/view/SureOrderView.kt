package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface SureOrderView : BaseView {

    fun onDoneCartResult(result: SureOrderBean){}

    fun onDefAddress(result: AddressBean)

    fun onCommitOrderResult(result: OrderDetailBean)
}