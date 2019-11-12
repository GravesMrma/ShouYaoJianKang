package com.wuhanzihai.rbk.ruibeikang.service.impl

import com.hhjt.baselibrary.ext.convert
import com.wuhanzihai.rbk.ruibeikang.data.repository.UserRepository
import com.wuhanzihai.rbk.ruibeikang.service.UserService
import com.hhjt.baselibrary.ext.convertT
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
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

    override fun activation(req: ActivationReq): Observable<BaseData> {
        return repository.activation(req).convertT()
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

    override fun upAddress(req: UpdateAddressReq): Observable<BaseData> {
        return repository.upAddress(req).convertT()
    }

    override fun getOrder(req: OrderReq): Observable<OrderBean> {
        return repository.getOrder(req).convert()
    }

    override fun userAdv(): Observable<MineAdv> {
        return repository.userAdv().convert()
    }

    override fun mineIndex(): Observable<MineBean> {
        return repository.mineIndex().convert()
    }

    override fun getVersion(): Observable<VersionBean> {
        return repository.getVersion().convert()
    }

    override fun disbutorIndex(): Observable<RebateBean> {
        return repository.disbutorIndex().convert()
    }

    override fun disbutorIndex1(): Observable<MineBean> {
        return repository.disbutorIndex1().convert()
    }

    override fun disbutorIndex2(): Observable<MineBean> {
        return repository.disbutorIndex2().convert()
    }

    override fun disbutorIndex3(): Observable<MineBean> {
        return repository.disbutorIndex3().convert()
    }

    override fun disbutorIndex4(): Observable<MineBean> {
        return repository.disbutorIndex4().convert()
    }

    override fun disbutorIndex5(): Observable<MineBean> {
        return repository.disbutorIndex5().convert()
    }

    override fun disbutorIndex6(): Observable<MineBean> {
        return repository.disbutorIndex6().convert()
    }

    override fun disbutorIndex7(): Observable<MineBean> {
        return repository.disbutorIndex7().convert()
    }

    override fun disbutorIndex8(): Observable<MineBean> {
        return repository.disbutorIndex8().convert()
    }

    override fun disbutorIndex9(): Observable<MineBean> {
        return repository.disbutorIndex9().convert()
    }
}
