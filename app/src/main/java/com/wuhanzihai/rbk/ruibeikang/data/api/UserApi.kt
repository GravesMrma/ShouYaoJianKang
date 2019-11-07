package com.wuhanzihai.rbk.ruibeikang.data.api

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface UserApi {

    @POST("Entrance/loginsendsms")
    fun sendCode(@Body req: GetCodeReq): Observable<BaseData>

    @POST("Entrance/quickLogin")
    fun login(@Body req: LoginReq): Observable<BaseResp<LoginData>>

    @POST("User/usertocard")
    fun activation(@Body req: ActivationReq): Observable<BaseData>

    @POST("User/saveinfo")
    fun saveInfo(@Body userInfoReq: UserInfoReq): Observable<BaseData>

    @POST("user/userinfo")
    fun getUserInfo(@Body req: GetUserInfoReq): Observable<BaseResp<LoginData>>

    @POST("User/useradd")
    fun addAddress(@Body req: AddAddressReq): Observable<BaseData>

    @POST("User/addresslist")
    fun getAddressList(@Body req: NoParamIdReq): Observable<BaseResp<MutableList<AddressBean>>>

    @POST("User/addressdefault")
    fun getDefAddress(@Body req: NoParamIdReq): Observable<BaseResp<AddressBean>>

    @POST("User/getoneadress")
    fun getAddressInfo(@Body req: AddressReq): Observable<BaseResp<AddressBean>>

    @POST("User/modifiaddress")
    fun upAddress(@Body req: UpdateAddressReq): Observable<BaseData>

    @POST("User/order")
    fun getOrder(@Body req: OrderReq): Observable<BaseResp<OrderBean>>

    //个人中心健康推荐
    @POST("User/healthrommend")
    fun mineIndex(@Body req: NoParamIdReq): Observable<BaseResp<MineBean>>

    @POST("Web/version")
    fun getVersion(@Body req: NoParamReq): Observable<BaseResp<VersionBean>>

    @Multipart
    @POST("HomeApi/UploadImg")
    fun uploadImg(@PartMap maps: HashMap<String, RequestBody>): Observable<BaseData>

}