package com.coolhabit.mocktv.domain.usecases

import com.coolhabit.mocktv.domain.api.IChannelsApiService
import com.coolhabit.mocktv.domain.api.IDatabaseStorage
import com.coolhabit.mocktv.domain.entities.TvChannel
import javax.inject.Inject

class ChannelsUseCase @Inject constructor(
    private val channelApi: IChannelsApiService,
    private val database: IDatabaseStorage,
) {

    suspend fun loadChannelsList(searchPattern: String?): List<TvChannel> {
        val favList = database.getFavoriteChannels()
        val currentList = channelApi.getChannels(searchPattern)
        currentList.map {
            it.isFavorite = favList.any { fav -> fav.channelName == it.channelName }
        }
        return currentList
    }

    suspend fun loadFavoriteChannels(searchPattern: String?): List<TvChannel> {
        val result = if(searchPattern.isNullOrEmpty()) {
            database.getFavoriteChannels()
        } else {
            database.getFavoriteChannelsByName(searchPattern)
        }
        return result
    }

    suspend fun addChannelToFav(channel: TvChannel) {
        database.addChannelToFavorite(channel)
    }

    suspend fun removeChannelFromFav(channel: TvChannel) {
        database.removeChannelFromFavorite(channel)
    }

    suspend fun loadChannelById(channelId: Int): TvChannel {
        return channelApi.getChannelById(channelId)
    }
}
