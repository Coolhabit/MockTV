package com.coolhabit.mocktv.data.network.mappers

import com.coolhabit.mocktv.data.network.entities.RemoteChannel
import com.coolhabit.mocktv.domain.entities.TvChannel

fun RemoteChannel.toDomain(): TvChannel {
    return TvChannel(
        channelId = this.id,
        channelName = this.name_ru,
        channelLogo = this.image,
        currentProgram = this.current.title,
        streamUrl = this.url,
        isFavorite = false,
    )
}
