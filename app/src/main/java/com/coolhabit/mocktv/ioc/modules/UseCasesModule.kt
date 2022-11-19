package com.coolhabit.mocktv.ioc.modules

import com.coolhabit.mocktv.domain.api.IChannelsApiService
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
    ) = ChannelsUseCase(api)
}
