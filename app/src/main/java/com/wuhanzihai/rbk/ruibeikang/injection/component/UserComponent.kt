package com.wuhanzihai.rbk.ruibeikang.injection.component

import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.fragment.*
import com.xidebao.activity.QRCodeScanActivity
import dagger.Component

@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(UserModule::class)])
interface UserComponent {

    fun inject(activity: LoginActivity)

    fun inject(activity: SetSexActivity)

    fun inject(activity: AddAddressActivity)

    fun inject(activity: AddressActivity)

    fun inject(fragment: MineFragment)

    fun inject(activity: OrderActivity)

    fun inject(activity: OrderDetailActivity)

    fun inject(fragment: OrderFragment)

    fun inject(fragment: OrderServiceFragment)

    fun inject(activity: QRCodeScanActivity)

    fun inject(activity: EditUserInfoActivity)

    fun inject(activity: OrderServiceDetailActivity)

    fun inject(activity: SetTagActivity)

    fun inject(activity: RebateActivity)

    fun inject(fragment: PhoneNumberFragment)

    fun inject(activity: BankCardActivity)

    fun inject(activity: AddBankCardActivity)

    fun inject(activity: ApplyCashRecordFragment)

    fun inject(fragment: DirectFragment)

    fun inject(fragment: IndirectFragment)

    fun inject(activity: DirectlySuperiorActivity)

    fun inject(activity: RebateAddressActivity)

    fun inject(activity: RebateAuthActivity)
}