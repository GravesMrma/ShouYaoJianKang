package com.wuhanzihai.rbk.ruibeikang.service

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable

interface MallService {

    fun mallIndex(): Observable<MallBean>

    fun goodsDetail(goodsDetailReq: GoodsDetailReq): Observable<GoodsDetailBean>

    fun addCart( req: AddCartReq): Observable<BaseData>

    fun shoppingCart( ): Observable<MutableList<ShoppingCartBean>>

    fun doneCart( req: DoneCartReq): Observable<SureOrderBean>

}