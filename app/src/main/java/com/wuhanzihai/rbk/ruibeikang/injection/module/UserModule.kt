package com.wuhanzihai.rbk.ruibeikang.injection.module

import com.wuhanzihai.rbk.ruibeikang.service.UserService
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @Provides
    fun providesUserService(userServiceImpl: UserServiceImpl): UserService {
        return userServiceImpl
    }
}