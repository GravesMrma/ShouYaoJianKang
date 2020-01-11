package com.wuhanzihai.rbk.ruibeikang.data.repository

import com.wuhanzihai.rbk.ruibeikang.data.api.UserApi
import com.hhjt.baselibrary.data.net.RetrofitFactory
import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.api.MallApi
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import javax.inject.Inject

class MallRepository @Inject constructor() {


    fun mallIndex(): Observable<BaseResp<MallBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).mallIndex(MallReq())
    }

    fun goodsDetail(goodsDetailReq: GoodsDetailReq): Observable<BaseResp<GoodsDetailBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).goodsDetail(goodsDetailReq)
    }

    fun buyGoods(req: BuyGoodsReq): Observable<BaseResp<GoodsBuyBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).buyGoods(req)
    }

    fun commitBuyGoods(req: CommitBuyGoodsReq): Observable<BaseResp<OrderDetailBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).commitBuyGoods(req)
    }

    fun addCart(req: AddCartReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(MallApi::class.java).addCart(req)
    }

    fun shoppingCart(): Observable<BaseResp<MutableList<ShoppingCartBean>>> {
        return RetrofitFactory.instance.create(MallApi::class.java).shoppingCart(NoParamIdReq())
    }

    fun doneCart(req: DoneCartReq): Observable<BaseResp<SureOrderBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).doneCart(req)
    }

    fun deleteCart(req: DoneCartReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(MallApi::class.java).deleteCart(req)
    }

    fun doneCartNum(req: DoneCartNumReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(MallApi::class.java).doneCartNum(req)
    }

    fun commitOrder(req: CommitOrderReq): Observable<BaseResp<OrderDetailBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).commitOrder(req)
    }

    fun payOrder(req: PayOrderReq): Observable<BaseResp<OrderPayBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).payOrder(req)
    }

    fun goodsClass(): Observable<BaseResp<MutableList<GoodsClassBean>>> {
        return RetrofitFactory.instance.create(MallApi::class.java).goodsClass(NoParamReq())
    }

    fun getGoodsList( req: GoodsReq): Observable<BaseResp<GoodsResult>> {
        return RetrofitFactory.instance.create(MallApi::class.java).getGoodsList(req)
    }

    fun searchWords(): Observable<BaseResp<SearchBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).searchWords(NoParamIdReq())
    }

    fun searchGoods(req:SearchReq): Observable<BaseResp<GoodsResult>> {
        return RetrofitFactory.instance.create(MallApi::class.java).searchGoods(req)
    }

    fun getGoodsClassList(req:GoodsClassListReq): Observable<BaseResp<MutableList<Child>>> {
        return RetrofitFactory.instance.create(MallApi::class.java).getGoodsClassList(req)
    }

    fun singleTravel(req:Int): Observable<BaseResp<TravelBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).singleTravel(NoParamPageReq(req))
    }

    fun getCartNumber(): Observable<BaseResp<CartNumberBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).getCartNumber(NoParamIdReq())
    }

    fun getCouponGoods(req:CouponGoodsReq): Observable<BaseResp<GoodsResult>> {
        return RetrofitFactory.instance.create(MallApi::class.java).getCouponGoods(req)
    }
}