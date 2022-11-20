package com.coolhabit.mocktv.data.network.services

import com.coolhabit.mocktv.data.network.ChannelsApi
import com.coolhabit.mocktv.data.network.mappers.toDomain
import com.coolhabit.mocktv.domain.entities.TvChannel
import com.coolhabit.mocktv.domain.api.IChannelsApiService

class ChannelsApiService(private val api: ChannelsApi) : IChannelsApiService {

    override suspend fun getChannels(searchPattern: String?): List<TvChannel> {
        val result = if(searchPattern.isNullOrEmpty()) {
            api.getChannels().channels
        } else {
            api.getChannels().channels.filter {
                it.name_ru.contains(searchPattern, true)
            }
        }
        return result.map {
            it.toDomain()
        }
    }

    override suspend fun getChannelById(channelId: Int): TvChannel {
        return api.getChannels().channels.first {
            it.id == channelId
        }.toDomain()
    }
}
