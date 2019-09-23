package com.wuhanzihai.rbk.ruibeikang.injection.component

import com.wuhanzihai.rbk.ruibeikang.activity.LoginActivity
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.wuhanzihai.rbk.ruibeikang.activity.MainActivity
import com.wuhanzihai.rbk.ruibeikang.fragment.MainFragment
import com.wuhanzihai.rbk.ruibeikang.injection.module.IndexModule
import dagger.Component

@PerComponentScope
@Component(dependencies = [(ActivityComponent::class)], modules = [(IndexModule::class)])
interface IndexComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: MainFragment)
}