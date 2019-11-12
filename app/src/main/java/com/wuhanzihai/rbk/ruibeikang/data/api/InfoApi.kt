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

interface InfoApi {

    @POST("Task/healthfive")
    fun healthIndex(@Body req: NoParamReq): Observable<BaseResp<HealthIndexBean>>

    @POST("ArticleContent/knowLedgeCategory")
    fun healthInfoClass(@Body req: NoParamReq): Observable<BaseResp<MutableList<HealthTitleBean>>>

    @POST("ArticleContent/onearticlelist")
    fun healthFoodClass(@Body req: IdReq): Observable<BaseResp<HealthFoodBean>>

    @POST("ArticleContent/articlelist")
    fun healthInfoList(@Body req: HealthListReq): Observable<BaseResp<HealthListBean>>

    // 文章搜索热词
    @POST("ArticleContent/searchhotkeyword")
    fun searchWords(@Body req: NoParamIdReq): Observable<BaseResp<SearchBean>>

    @POST("ArticleContent/articlesearch")
    fun searchInfo(@Body req: SearchReq): Observable<BaseResp<HealthListBean>>

    // 课堂搜索词
    @POST("ArticleContent/searchmusichotkeyword")
    fun searchClassWords(@Body req: NoParamIdReq): Observable<BaseResp<SearchBean>>

    @POST("ArticleContent/musicsearch")
    fun searchClass(@Body req: SearchReq): Observable<BaseResp<HealthClassBean>>

    @POST("ArticleContent/articledetails")
    fun articleDetail(@Body req: ArticleDetailReq): Observable<BaseResp<ArticleDetailBean>>

    @POST("ArticleContent/articleRecommend")
    fun healthInfoBanner(@Body req: HealthBannerReq): Observable<BaseResp<HealthBannerBean>>

    @POST("ArticleContent/healthHabitsCategory")
    fun healthHabitsList(@Body req: NoParamReq): Observable<BaseResp<MutableList<HealthHabitsBean>>>

    @POST("ArticleContent/articleCategory")
    fun healthHabitsDetailList(@Body req: HealthHabitsDetailReq): Observable<BaseResp<MutableList<HealthHabitsBean>>>

    // 收藏与取消收藏
    @POST("ArticleContent/articlecollection")
    fun CollectAtr(@Body req: CollectAtrReq): Observable<BaseData>

    // 收藏列表
    @POST("User/collect")
    fun collectList(@Body req: CollectListReq): Observable<BaseResp<MutableList<CollectBean>>>

    // 点赞与取消收藏
    @POST("ArticleContent/praise")
    fun LikeAtr(@Body req: LikeAtrReq): Observable<BaseData>

//   音乐接口
    @POST("ArticleContent/recommendmusic")
    fun musicBanner(@Body req: MusicBannerReq): Observable<BaseResp<MusicBannerBean>>

    @POST("ArticleContent/musiccategory")
    fun musicClass(@Body req: NoParamReq): Observable<BaseResp<MutableList<MusicClassBean>>>

    @POST("ArticleContent/categorymusic")
    fun musicList(@Body req: MusicReq): Observable<BaseResp<MusicDetailBean>>

    // 健康讲堂

    @POST("ArticleContent/courselist")
    fun healthClass(@Body req: NoParamPageReq): Observable<BaseResp<HealthClassBean>>

    @POST("ArticleContent/coursebanner")
    fun healthBanner(@Body req: NoParamReq): Observable<BaseResp<HealthClassBannerBean>>

    @POST("ArticleContent/coursedetails")
    fun healthClassDetail(@Body req: HealthClassDetailReq): Observable<BaseResp<HealthClassDetailBean>>

    @POST("ArticleContent/coursedetailsmusic")
    fun healthClassDetailMusic(@Body req: HealthClassDetailMusicReq): Observable<BaseResp<HealthClassDetailMusicBean>>

    @POST("ArticleContent/getarticlekeys")
    fun tagWords(@Body req: NoParamReq): Observable<BaseResp<MineAdv>>

}