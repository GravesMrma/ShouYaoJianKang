package com.wuhanzihai.rbk.ruibeikang.presenter

import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.GoodsDetailView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MallView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class GoodsDetailPresenter @Inject constructor() : BasePresenter<GoodsDetailView>() {

    @Inject
    lateinit var mallServiceImpl: MallServiceImpl

    @Inject
    lateinit var userServiceImpl: UserServiceImpl


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

    fun goodsDetailRe(goodsDetailReq: GoodsDetailReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.goodsDetail(goodsDetailReq)
                .excute(object : BaseSubscriber<GoodsDetailBean>(mView) {
                    override fun onNext(t: GoodsDetailBean) {
                        super.onNext(t)
                        mView.onGoodsDetailResultRe(t)
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

    fun getShoppingCartList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.shoppingCart()
                .excute(object : BaseSubscriber<MutableList<ShoppingCartBean>>(mView) {
                    override fun onNext(t: MutableList<ShoppingCartBean>) {
                        super.onNext(t)
                        mView.onShoppingCartListResult(t)
                    }
                }, lifecycleProvider)
    }

    fun buyGoods(req: BuyGoodsReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.buyGoods(req)
                .excute(object : BaseSubscriber<GoodsBuyBean>(mView) {
                    override fun onNext(t: GoodsBuyBean) {
                        super.onNext(t)
                        mView.onBuyResult(t)
                    }
                }, lifecycleProvider)
    }

    fun getCartNumber() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.getCartNumber()
                .excute(object : BaseSubscriber<CartNumberBean>(mView) {
                    override fun onNext(t: CartNumberBean) {
                        super.onNext(t)
                        mView.onCartNumberResult(t)
                    }
                }, lifecycleProvider)
    }



    fun takeExchangeCoupons(req: CouponIdReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.takeExchangeCoupons(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onTakeCouponResult()
                    }
                }, lifecycleProvider)
    }

}