package com.coolhabit.mocktv.channels.presentation

import com.coolhabit.mocktv.baseUI.model.NavCommand

interface IChannelsListRouter {

    fun navigateToTvStream(channelId: Int): NavCommand
}
