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

    fun addCart(req: AddCartReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(MallApi::class.java).addCart(req)
    }

    fun shoppingCart(): Observable<BaseResp<MutableList<ShoppingCartBean>>> {
        return RetrofitFactory.instance.create(MallApi::class.java).shoppingCart(NoParamIdReq())
    }

    fun doneCart(req: DoneCartReq): Observable<BaseResp<SureOrderBean>> {
        return RetrofitFactory.instance.create(MallApi::class.java).doneCart(req)
    }
}