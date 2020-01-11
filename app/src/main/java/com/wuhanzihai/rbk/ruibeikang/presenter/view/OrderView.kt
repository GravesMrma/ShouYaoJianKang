package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface OrderView : BaseView {

    fun onOrderResult(result: OrderBean){}

    fun onOrderSure(){}

    fun onOrderDelete(){}

    fun onOrderClose(){}

}