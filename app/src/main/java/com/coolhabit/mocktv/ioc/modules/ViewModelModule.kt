package com.coolhabit.mocktv.ioc.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coolhabit.mocktv.channels.presentation.ChannelsListViewModel
import com.coolhabit.mocktv.channels.presentation.base.ChannelsBaseViewModel
import com.coolhabit.mocktv.channels.presentation.favs.FavsListViewModel
import com.coolhabit.mocktv.ioc.utils.ViewModelFactory
import com.coolhabit.mocktv.ioc.utils.ViewModelKey
import com.coolhabit.mocktv.presentation.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun mainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChannelsListViewModel::class)
    abstract fun channelsListViewModel(viewModel: ChannelsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChannelsBaseViewModel::class)
    abstract fun channelsBaseViewModel(viewModel: ChannelsBaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavsListViewModel::class)
    abstract fun favsListViewModel(viewModel: FavsListViewModel): ViewModel
}
