package com.coolhabit.mocktv.channels.presentation.extensions

import com.coolhabit.mocktv.baseUI.model.TvChannelUI
import com.coolhabit.mocktv.domain.entities.TvChannel

fun TvChannel.toUiModel(): TvChannelUI {
    return TvChannelUI(
        id = this.channelId,
        name = this.channelName,
        logo = this.channelLogo,
        program = this.currentProgram,
        url = this.streamUrl,
    )
}
