package com.wuhanzihai.rbk.ruibeikang.service.impl

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.ext.convert
import com.wuhanzihai.rbk.ruibeikang.data.repository.UserRepository
import com.wuhanzihai.rbk.ruibeikang.service.UserService
import com.hhjt.baselibrary.ext.convertT
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.data.repository.InfoRepository
import com.wuhanzihai.rbk.ruibeikang.data.repository.MallRepository
import com.wuhanzihai.rbk.ruibeikang.service.IndexService
import com.wuhanzihai.rbk.ruibeikang.service.MallService
import io.reactivex.Observable
import javax.inject.Inject

class MallServiceImpl @Inject constructor() : MallService {

    @Inject
    lateinit var repository: MallRepository

    override fun mallIndex(): Observable<MallBean> {
        return repository.mallIndex().convert()
    }

    override fun goodsDetail(goodsDetailReq: GoodsDetailReq): Observable<GoodsDetailBean> {
        return repository.goodsDetail(goodsDetailReq).convert()
    }

    override fun addCart(req: AddCartReq): Observable<BaseData> {
        return repository.addCart(req).convertT()
    }

    override fun shoppingCart(): Observable<MutableList<ShoppingCartBean>> {
        return repository.shoppingCart().convert()
    }

    override fun doneCart(req: DoneCartReq): Observable<SureOrderBean> {
        return repository.doneCart(req).convert()
    }
}
