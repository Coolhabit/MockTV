package com.coolhabit.mocktv.domain.api

import com.coolhabit.mocktv.domain.entities.TvChannel

interface IChannelsApiService {

    suspend fun getChannels(): List<TvChannel>
}
