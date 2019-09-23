package com.wuhanzihai.rbk.ruibeikang.data.api

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.TimeReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface IndexApi {

    @POST("Entrance/quickLogin")
    fun login(@Body req: LoginReq): Observable<BaseResp<LoginData>>

    @POST("other/homearticle")
    fun indexInfo(@Body req: NoParamReq): Observable<BaseResp<IndexBean>>

    @POST("other/homebanner")
    fun indexBanner(@Body req: NoParamReq): Observable<BaseResp<IndexBannerBean>>

    @POST("other/homead")
    fun indexAdv(@Body req: NoParamReq): Observable<BaseResp<IndexAdvBean>>

    @POST("other/healthycolck")
    fun indexClock(@Body req: TimeReq): Observable<BaseResp<IndexClockBean>>

}