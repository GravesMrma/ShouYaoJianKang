package com.wuhanzihai.rbk.ruibeikang.service.impl

import com.hhjt.baselibrary.ext.convert
import com.hhjt.baselibrary.ext.convertT
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.data.repository.MallRepository
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

    override fun buyGoods(req: BuyGoodsReq): Observable<GoodsBuyBean> {
        return repository.buyGoods(req).convert()
    }

    override fun commitBuyGoods(req: CommitBuyGoodsReq): Observable<OrderDetailBean> {
        return repository.commitBuyGoods(req).convert()
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

    override fun deleteCart(req: DoneCartReq): Observable<BaseData> {
        return repository.deleteCart(req).convertT()
    }

    override fun doneCartNum(req: DoneCartNumReq): Observable<BaseData> {
        return repository.doneCartNum(req).convertT()
    }

    override fun commitOrder(req: CommitOrderReq): Observable<OrderDetailBean> {
        return repository.commitOrder(req).convert()
    }

    override fun payOrder(req: PayOrderReq): Observable<OrderPayBean> {
        return repository.payOrder(req).convert()
    }

    override fun goodClass(): Observable<MutableList<GoodsClassBean>> {
        return repository.goodsClass().convert()
    }

    override fun getGoodsList(req: GoodsReq): Observable<GoodsResult> {
        return repository.getGoodsList(req).convert()
    }

    override fun searchWords(): Observable<SearchBean> {
        return repository.searchWords().convert()
    }

    override fun searchGoods(req: SearchReq): Observable<GoodsResult> {
        return repository.searchGoods(req).convert()
    }

    override fun getGoodsClassList(req: GoodsClassListReq): Observable<MutableList<Child>> {
        return repository.getGoodsClassList(req).convert()
    }

    override fun singleTravel(req: Int): Observable<TravelBean> {
        return repository.singleTravel(req).convert()
    }
}
