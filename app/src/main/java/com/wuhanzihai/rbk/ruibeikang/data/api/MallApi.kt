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

    @POST("Cart/usercart")
    fun addCart(@Body req: AddCartReq): Observable<BaseData>

    @POST("Cart/getusercart")
    fun shoppingCart(@Body req: NoParamIdReq): Observable<BaseResp<MutableList<ShoppingCartBean>>>

    @POST("Cart/cartsettiment")
    fun doneCart(@Body req: DoneCartReq): Observable<BaseResp<SureOrderBean>>


}