package com.wuhanzihai.rbk.ruibeikang.injection.component

import com.wuhanzihai.rbk.ruibeikang.activity.LoginActivity
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.wuhanzihai.rbk.ruibeikang.activity.AddAddressActivity
import com.wuhanzihai.rbk.ruibeikang.activity.AddressActivity
import com.wuhanzihai.rbk.ruibeikang.activity.SetSexActivity
import com.wuhanzihai.rbk.ruibeikang.fragment.MineFragment
import dagger.Component

@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(UserModule::class)])
interface UserComponent {

    fun inject(activity: LoginActivity)

    fun inject(activity: SetSexActivity)

    fun inject(activity: AddAddressActivity)

    fun inject(activity: AddressActivity)

    fun inject(fragment: MineFragment)

}