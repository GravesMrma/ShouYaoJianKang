package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface PayView : BaseView {


    fun onPayResult(result: OrderPayBean)
}