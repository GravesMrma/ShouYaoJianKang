package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface GoodsDetailView : BaseView {

    fun onGoodsDetailResult(result: GoodsDetailBean)

    fun onGoodsDetailResultRe(result: GoodsDetailBean){}

    fun onAddCartResult()

    fun onShoppingCartListResult(result: MutableList<ShoppingCartBean>) {}

    fun onBuyResult(result: GoodsBuyBean) {}

    fun onCartNumberResult(result: CartNumberBean) {}

    fun onTakeCouponResult() {}
}