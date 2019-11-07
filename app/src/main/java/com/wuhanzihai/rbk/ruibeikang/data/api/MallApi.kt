package com.wuhanzihai.rbk.ruibeikang.data.api

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MallApi {

    @POST("ShoppingMall/main")
    fun mallIndex(@Body req: MallReq): Observable<BaseResp<MallBean>>

    @POST("ShoppingMall/productdetails")
    fun goodsDetail(@Body req: GoodsDetailReq): Observable<BaseResp<GoodsDetailBean>>

    @POST("Cart/directPurchase")
    fun buyGoods(@Body req: BuyGoodsReq): Observable<BaseResp<GoodsBuyBean>>

    @POST("Cart/directPurchaseorder")
    fun commitBuyGoods(@Body req: CommitBuyGoodsReq): Observable<BaseResp<OrderDetailBean>>

    @POST("Cart/usercart")
    fun addCart(@Body req: AddCartReq): Observable<BaseData>

    @POST("Cart/getusercart")
    fun shoppingCart(@Body req: NoParamIdReq): Observable<BaseResp<MutableList<ShoppingCartBean>>>

    @POST("Cart/cartsettiment")
    fun doneCart(@Body req: DoneCartReq): Observable<BaseResp<SureOrderBean>>

    @POST("Cart/delusercart")
    fun deleteCart(@Body req: DoneCartReq): Observable<BaseData>

    @POST("Cart/usercartconfirm")
    fun doneCartNum(@Body req: DoneCartNumReq): Observable<BaseData>

    @POST("Cart/settlementOrder")
    fun commitOrder(@Body req: CommitOrderReq): Observable<BaseResp<OrderDetailBean>>

    @POST("Pay/paymethod")
    fun payOrder(@Body req: PayOrderReq): Observable<BaseResp<OrderPayBean>>

    @POST("ShoppingMall/category")
    fun goodsClass(@Body req: NoParamReq): Observable<BaseResp<MutableList<GoodsClassBean>>>

    @POST("ShoppingMall/productcategorylist")
    fun getGoodsList(@Body req: GoodsReq): Observable<BaseResp<GoodsResult>>

    @POST("ShoppingMall/findcategory")
    fun getGoodsClassList(@Body req: GoodsClassListReq): Observable<BaseResp<MutableList<Child>>>

    @POST("ShoppingMall/searchhotkeyword")
    fun searchWords(@Body req: NoParamIdReq): Observable<BaseResp<SearchBean>>

    // 商品搜索
    @POST("ShoppingMall/searchproduct")
    fun searchGoods(@Body req: SearchReq): Observable<BaseResp<GoodsResult>>


    @POST("ShoppingMall/travelreview")
    fun singleTravel(@Body req: NoParamPageReq): Observable<BaseResp<TravelBean>>

//    @POST("ShoppingMall/productcategorylist")
//    fun xibaoyingyang1(@Body req: GoodsReq): Observable<BaseResp<GoodsResult>>
//
//    @POST("ShoppingMall/productcategorylist")
//    fun xibaoyingyang2(@Body req: GoodsReq): Observable<BaseResp<GoodsResult>>
//
//    @POST("ShoppingMall/productcategorylist")
//    fun xibaoyingyang3(@Body req: GoodsReq): Observable<BaseResp<GoodsResult>>
//
//    @POST("ShoppingMall/productcategorylist")
//    fun xibaoyingyang4(@Body req: GoodsReq): Observable<BaseResp<GoodsResult>>

}