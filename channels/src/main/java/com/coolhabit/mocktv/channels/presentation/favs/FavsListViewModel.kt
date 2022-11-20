package com.coolhabit.mocktv.channels.presentation.favs

import androidx.lifecycle.viewModelScope
import com.coolhabit.mocktv.baseUI.model.StatefulData
import com.coolhabit.mocktv.baseUI.presentation.BaseViewModel
import com.coolhabit.mocktv.channels.presentation.IChannelsListRouter
import com.coolhabit.mocktv.domain.entities.TvChannel
import com.coolhabit.mocktv.domain.usecases.ChannelsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavsListViewModel @Inject constructor(
    private val useCase: ChannelsUseCase,
    private val router: IChannelsListRouter,
) : BaseViewModel() {

    private val _loadChannels = statefulSharedFlow<List<TvChannel>>()
    val loadChannels: Flow<StatefulData<List<TvChannel>>>
        get() = _loadChannels

    var prevQuery: String? = null

    fun initContent() {
        _loadChannels.fetch {
            useCase.loadFavoriteChannels(null)
        }
    }

    fun removeChannelFromFav(channel: TvChannel) {
        viewModelScope.launch {
            useCase.removeChannelFromFav(channel)
            initContent()
        }
    }

    fun performSearch(searchPattern: String) {
        if (prevQuery == searchPattern) {
            return
        }

        prevQuery = searchPattern
        _loadChannels.fetch {
            useCase.loadFavoriteChannels(searchPattern)
        }
    }

    fun navigateToTvStream(channelId: Int) {
        navigateTo(router.navigateToTvStream(channelId))
    }
}
