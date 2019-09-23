package com.wuhanzihai.rbk.ruibeikang.data.api

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @POST("Entrance/loginsendsms")
    fun sendCode(@Body req: GetCodeReq): Observable<BaseData>

    @POST("Entrance/quickLogin")
    fun login(@Body req: LoginReq): Observable<BaseResp<LoginData>>

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
    fun upAddress(@Body req: AddAddressReq): Observable<BaseData>

}