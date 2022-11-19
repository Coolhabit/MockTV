package com.coolhabit.mocktv.domain.entities

data class TvChannel(
    val channelId: Int,
    val channelName: String,
    val channelLogo: String,
    val currentProgram: String,
    val streamUrl: String,
)
