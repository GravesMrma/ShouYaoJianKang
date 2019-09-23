package com.wuhanzihai.rbk.ruibeikang.data.repository

import com.wuhanzihai.rbk.ruibeikang.data.api.UserApi
import com.hhjt.baselibrary.data.net.RetrofitFactory
import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import javax.inject.Inject

class UserRepository @Inject constructor() {

    fun sendCode(getCodeReq: GetCodeReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).sendCode(getCodeReq)
    }

    fun login(loginReq: LoginReq): Observable<BaseResp<LoginData>> {
        return RetrofitFactory.instance.create(UserApi::class.java).login(loginReq)
    }

    fun saveInfo(userInfoReq: UserInfoReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).saveInfo(userInfoReq)
    }

    fun getUserInfo(): Observable<BaseResp<LoginData>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getUserInfo(GetUserInfoReq())
    }

    fun addAddress(req: AddAddressReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).addAddress(req)
    }

    fun getAddressList(): Observable<BaseResp<MutableList<AddressBean>>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getAddressList(NoParamIdReq())
    }

    fun getDefAddress(): Observable<BaseResp<AddressBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getDefAddress(NoParamIdReq())
    }

    fun getAddressInfo(req: AddressReq): Observable<BaseResp<AddressBean>> {
        return RetrofitFactory.instance.create(UserApi::class.java).getAddressInfo(req)
    }

    fun upAddress(req: AddAddressReq): Observable<BaseData> {
        return RetrofitFactory.instance.create(UserApi::class.java).upAddress(req)
    }

}