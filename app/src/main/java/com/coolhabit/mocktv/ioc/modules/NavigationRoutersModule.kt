package com.coolhabit.mocktv.ioc.modules

import android.content.Context
import com.coolhabit.mocktv.channels.presentation.IChannelsListRouter
import com.coolhabit.mocktv.navigation.ChannelsListRouterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationRoutersModule {

    @Provides
    @Singleton
    fun provideChannelsListRouter(context: Context): IChannelsListRouter = ChannelsListRouterImpl(context)
}
