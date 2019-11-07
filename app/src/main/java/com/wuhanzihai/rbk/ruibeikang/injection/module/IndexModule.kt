package com.wuhanzihai.rbk.ruibeikang.injection.module

import com.wuhanzihai.rbk.ruibeikang.service.IndexService
import com.wuhanzihai.rbk.ruibeikang.service.impl.IndexServiceImpl
import dagger.Module
import dagger.Provides

@Module
class IndexModule {

    @Provides
    fun providesIndexService(indexServiceImpl: IndexServiceImpl): IndexService {
        return indexServiceImpl
    }
}