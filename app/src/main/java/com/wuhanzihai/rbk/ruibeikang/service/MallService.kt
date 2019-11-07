package com.wuhanzihai.rbk.ruibeikang.service

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable

interface MallService {

    fun mallIndex(): Observable<MallBean>

    fun goodsDetail(goodsDetailReq: GoodsDetailReq): Observable<GoodsDetailBean>

    fun buyGoods(req: BuyGoodsReq): Observable<GoodsBuyBean>

    fun commitBuyGoods(req: CommitBuyGoodsReq): Observable<OrderDetailBean>

    fun addCart(req: AddCartReq): Observable<BaseData>

    fun shoppingCart(): Observable<MutableList<ShoppingCartBean>>

    fun doneCart(req: DoneCartReq): Observable<SureOrderBean>

    fun deleteCart(req: DoneCartReq): Observable<BaseData>

    fun doneCartNum(req: DoneCartNumReq): Observable<BaseData>

    fun commitOrder(req: CommitOrderReq): Observable<OrderDetailBean>

    fun payOrder(req: PayOrderReq): Observable<OrderPayBean>

    fun goodClass(): Observable<MutableList<GoodsClassBean>>

    fun getGoodsList(req: GoodsReq): Observable<GoodsResult>

    fun searchWords(): Observable<SearchBean>

    fun searchGoods(req:SearchReq): Observable<GoodsResult>

    fun getGoodsClassList(req:GoodsClassListReq): Observable<MutableList<Child>>

    fun singleTravel(req:Int): Observable<TravelBean>

}