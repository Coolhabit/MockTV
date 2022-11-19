package com.coolhabit.mocktv.ioc.modules

import com.coolhabit.mocktv.channels.presentation.ChannelsListFragment
import com.coolhabit.mocktv.channels.presentation.base.ChannelsBaseFragment
import com.coolhabit.mocktv.channels.presentation.favs.FavsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class, ActivityModule::class])
abstract class FragmentsModule {

    @ContributesAndroidInjector
    abstract fun provideChannelsListFragment(): ChannelsListFragment

    @ContributesAndroidInjector
    abstract fun provideChannelsBaseFragment(): ChannelsBaseFragment

    @ContributesAndroidInjector
    abstract fun provideFavsListFragment(): FavsListFragment
}
