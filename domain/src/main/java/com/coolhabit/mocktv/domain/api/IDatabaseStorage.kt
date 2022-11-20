package com.coolhabit.mocktv.domain.api

import com.coolhabit.mocktv.domain.entities.TvChannel

interface IDatabaseStorage {

    suspend fun addChannelToFavorite(channel: TvChannel)

    suspend fun removeChannelFromFavorite(channel: TvChannel)

    suspend fun getFavoriteChannels(): List<TvChannel>

    suspend fun getFavoriteChannelsByName(searchPattern: String): List<TvChannel>
}
