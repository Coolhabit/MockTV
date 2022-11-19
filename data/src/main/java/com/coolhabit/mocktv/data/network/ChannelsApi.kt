package com.coolhabit.mocktv.data.network

import com.coolhabit.mocktv.data.network.entities.ChannelsResponse
import retrofit2.http.GET

interface ChannelsApi {

    @GET("playlist/channels.json")
    suspend fun getChannels(): ChannelsResponse
}
