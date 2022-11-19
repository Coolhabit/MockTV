package com.coolhabit.mocktv.domain.usecases

import com.coolhabit.mocktv.domain.api.IChannelsApiService
import com.coolhabit.mocktv.domain.entities.TvChannel
import javax.inject.Inject

class ChannelsUseCase @Inject constructor(
    private val channelApi: IChannelsApiService,
) {

    suspend fun loadChannelsList(): List<TvChannel> {
        return channelApi.getChannels()
    }
}
