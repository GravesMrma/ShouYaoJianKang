package com.wuhanzihai.rbk.ruibeikang.injection.component

import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.fragment.MineFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.OrderFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.OrderServiceFragment
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

}