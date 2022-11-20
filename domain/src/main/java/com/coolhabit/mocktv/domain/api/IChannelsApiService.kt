package com.coolhabit.mocktv.domain.api

import com.coolhabit.mocktv.domain.entities.TvChannel

interface IChannelsApiService {

    suspend fun getChannels(searchPattern: String?): List<TvChannel>

    suspend fun getChannelById(channelId: Int): TvChannel
}
