package com.wuhanzihai.rbk.ruibeikang.data.repository

import com.hhjt.baselibrary.data.net.RetrofitFactory
import com.hhjt.baselibrary.data.protocol.BaseResp
import com.wuhanzihai.rbk.ruibeikang.data.api.IndexApi
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.TimeReq
import io.reactivex.Observable
import javax.inject.Inject

class IndexRepository @Inject constructor() {

    fun login(loginReq: LoginReq): Observable<BaseResp<LoginData>> {
        return RetrofitFactory.instance.create(IndexApi::class.java).login(loginReq)
    }

    fun indexInfo(): Observable<BaseResp<IndexBean>> {
        return RetrofitFactory.instance.create(IndexApi::class.java).indexInfo(NoParamReq())
    }

    fun indexAdv(): Observable<BaseResp<IndexAdvBean>> {
        return RetrofitFactory.instance.create(IndexApi::class.java).indexAdv(NoParamReq())
    }

    fun indexBanner(): Observable<BaseResp<IndexBannerBean>> {
        return RetrofitFactory.instance.create(IndexApi::class.java).indexBanner(NoParamReq())
    }

    fun indexClock(): Observable<BaseResp<IndexClockBean>> {
        return RetrofitFactory.instance.create(IndexApi::class.java).indexClock(TimeReq())
    }
}