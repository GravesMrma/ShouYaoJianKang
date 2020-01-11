package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface CouponView : BaseView {

    fun onCouponResult(result: MutableList<CouponBean>){}

    fun onExchangeCouponResult(result: MutableList<GoodsCouponBean>){}

    fun onTakeCouponResult(){}

}