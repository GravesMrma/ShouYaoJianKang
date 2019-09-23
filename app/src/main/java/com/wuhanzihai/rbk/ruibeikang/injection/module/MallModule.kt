package com.wuhanzihai.rbk.ruibeikang.injection.module

import com.wuhanzihai.rbk.ruibeikang.service.IndexService
import com.wuhanzihai.rbk.ruibeikang.service.InfoService
import com.wuhanzihai.rbk.ruibeikang.service.MallService
import com.wuhanzihai.rbk.ruibeikang.service.impl.IndexServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class MallModule {

    @Provides
    fun providesMallService(mallServiceImpl: MallServiceImpl): MallService {
        return mallServiceImpl
    }
}