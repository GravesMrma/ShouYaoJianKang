package com.wuhanzihai.rbk.ruibeikang.data.api

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface InfoApi {

    @POST("ArticleContent/knowLedgeCategory")
    fun healthInfoClass(@Body req: NoParamReq): Observable<BaseResp<MutableList<HealthTitleBean>>>

    @POST("ArticleContent/articlelist")
    fun healthInfoList(@Body req: HealthListReq): Observable<BaseResp<HealthListBean>>

    @POST("ArticleContent/articledetails")
    fun articleDetail(@Body req: ArticleDetailReq): Observable<BaseResp<ArticleDetailBean>>

    @POST("ArticleContent/articleRecommend")
    fun healthInfoBanner(@Body req: HealthBannerReq): Observable<BaseResp<HealthBannerBean>>

    @POST("ArticleContent/healthHabitsCategory")
    fun healthHabitsList(@Body req: NoParamReq): Observable<BaseResp<MutableList<HealthHabitsBean>>>

    @POST("ArticleContent/articleCategory")
    fun healthHabitsDetailList(@Body req: HealthHabitsDetailReq): Observable<BaseResp<MutableList<HealthHabitsBean>>>

}