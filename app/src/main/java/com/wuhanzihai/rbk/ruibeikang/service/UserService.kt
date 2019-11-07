package com.wuhanzihai.rbk.ruibeikang.service

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable

interface UserService {

    fun sendCode(getCodeReq: GetCodeReq): Observable<BaseData>

    fun login(loginReq: LoginReq): Observable<LoginData>

    fun activation(getCodeReq: ActivationReq): Observable<BaseData>

    fun saveInfo(userInfoReq: UserInfoReq): Observable<BaseData>

    fun getUserInfo(): Observable<LoginData>

    fun addAddress(req: AddAddressReq): Observable<BaseData>

    fun getAddressList(): Observable<MutableList<AddressBean>>

    fun getDefAddress(): Observable<AddressBean>

    fun getAddressInfo(req: AddressReq): Observable<AddressBean>

    fun upAddress(req: UpdateAddressReq): Observable<BaseData>

    fun getOrder(req: OrderReq): Observable<OrderBean>

    fun mineIndex(): Observable<MineBean>

    fun getVersion(): Observable<VersionBean>

}