package com.coolhabit.mocktv.channels.presentation.favs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolhabit.mocktv.domain.entities.TvChannel
import com.coolhabit.mocktv.domain.usecases.ChannelsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavsListViewModel @Inject constructor(
    private val useCase: ChannelsUseCase,
) : ViewModel() {

    private val _loadChannels = MutableSharedFlow<List<TvChannel>>()
    val loadChannels = _loadChannels.asSharedFlow()

    var prevQuery: String? = null

    fun initContent() {
        viewModelScope.launch {
            _loadChannels.emit(useCase.loadFavoriteChannels(null))
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
        viewModelScope.launch {
            _loadChannels.emit(useCase.loadFavoriteChannels(searchPattern))
        }
    }
}
