package com.coolhabit.mocktv.ioc.modules

import com.coolhabit.mocktv.data.network.ChannelsApi
import com.coolhabit.mocktv.data.network.services.ChannelsApiService
import com.coolhabit.mocktv.domain.api.IChannelsApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideChannelsApiService(api: ChannelsApi): IChannelsApiService = ChannelsApiService(api)
}
