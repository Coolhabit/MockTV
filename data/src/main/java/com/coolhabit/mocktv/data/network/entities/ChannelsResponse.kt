package com.coolhabit.mocktv.data.network.entities

data class ChannelsResponse(
    val channels: List<RemoteChannel>,
    val ckey: String,
    val valid: Int
)
