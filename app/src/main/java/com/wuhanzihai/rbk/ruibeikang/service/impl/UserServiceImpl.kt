package com.wuhanzihai.rbk.ruibeikang.service.impl

import com.hhjt.baselibrary.data.protocol.BaseResp
import com.hhjt.baselibrary.ext.convert
import com.wuhanzihai.rbk.ruibeikang.data.repository.UserRepository
import com.wuhanzihai.rbk.ruibeikang.service.UserService
import com.hhjt.baselibrary.ext.convertT
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import io.reactivex.Observable
import javax.inject.Inject

class UserServiceImpl @Inject constructor() : UserService {

    @Inject
    lateinit var repository: UserRepository

    override fun sendCode(getCodeReq: GetCodeReq): Observable<BaseData> {
        return repository.sendCode(getCodeReq).convertT()
    }

    override fun login(loginReq: LoginReq): Observable<LoginData> {
        return repository.login(loginReq).convert()
    }

    override fun saveInfo(userInfoReq: UserInfoReq): Observable<BaseData> {
        return repository.saveInfo(userInfoReq).convertT()
    }

    override fun getUserInfo(): Observable<LoginData> {
        return repository.getUserInfo().convert()
    }

    override fun addAddress(req: AddAddressReq): Observable<BaseData> {
        return repository.addAddress(req).convertT()
    }

    override fun getAddressList(): Observable<MutableList<AddressBean>> {
        return repository.getAddressList().convert()
    }

    override fun getDefAddress(): Observable<AddressBean> {
        return repository.getDefAddress().convert()
    }

    override fun getAddressInfo(req: AddressReq): Observable<AddressBean> {
        return repository.getAddressInfo(req).convert()
    }

    override fun upAddress(req: AddAddressReq): Observable<BaseData> {
        return repository.upAddress(req).convertT()
    }
}
