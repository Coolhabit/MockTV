package com.coolhabit.mocktv.data.network.services

import com.coolhabit.mocktv.data.network.ChannelsApi
import com.coolhabit.mocktv.data.network.mappers.toDomain
import com.coolhabit.mocktv.domain.entities.TvChannel
import com.coolhabit.mocktv.domain.api.IChannelsApiService

class ChannelsApiService(private val api: ChannelsApi) : IChannelsApiService {

    override suspend fun getChannels(): List<TvChannel> {
        val result = api.getChannels().channels.map {
            it.toDomain()
        }
        return result
    }
}
