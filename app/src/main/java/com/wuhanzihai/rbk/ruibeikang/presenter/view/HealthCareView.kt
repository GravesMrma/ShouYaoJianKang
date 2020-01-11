package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface HealthCareView :BaseView {

    fun onGoodsClassResult(result: MutableList<GoodsClassBean>){}

    fun onGoodsListResult(result: GoodsResult){}

    fun onGoodsListClassResult(result:MutableList<Child>){}

    fun onCouponGoodsResult(result: GoodsResult){}
}