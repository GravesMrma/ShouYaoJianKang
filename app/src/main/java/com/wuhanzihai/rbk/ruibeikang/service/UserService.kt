package com.wuhanzihai.rbk.ruibeikang.service

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable

interface UserService {

    fun sendCode(getCodeReq: GetCodeReq): Observable<BaseData>

    fun login(loginReq: LoginReq): Observable<LoginData>

    fun saveInfo(userInfoReq: UserInfoReq): Observable<BaseData>

    fun getUserInfo(): Observable<LoginData>

    fun addAddress(req: AddAddressReq): Observable<BaseData>

    fun getAddressList(): Observable<MutableList<AddressBean>>

    fun getDefAddress(): Observable<AddressBean>

    fun getAddressInfo(req: AddressReq): Observable<AddressBean>

    fun upAddress(req: AddAddressReq): Observable<BaseData>
}