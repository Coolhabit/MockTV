package com.coolhabit.mocktv.ioc.modules

import com.coolhabit.mocktv.domain.api.IChannelsApiService
import com.coolhabit.mocktv.domain.api.IDatabaseStorage
import com.coolhabit.mocktv.domain.usecases.ChannelsUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun provideChannelsUseCase(
        api: IChannelsApiService,
        database: IDatabaseStorage,
    ) = ChannelsUseCase(api, database)
}
