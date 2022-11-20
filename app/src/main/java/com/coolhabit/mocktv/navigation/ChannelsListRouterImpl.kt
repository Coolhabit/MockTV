package com.coolhabit.mocktv.navigation

import android.content.Context
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import com.coolhabit.mocktv.baseUI.model.NavCommand
import com.coolhabit.mocktv.channels.presentation.IChannelsListRouter

class ChannelsListRouterImpl(private val context: Context) : IChannelsListRouter {

    override fun navigateToTvStream(channelId: Int): NavCommand {
        return NavCommand.Deeplink(
            NavDeepLinkRequest.Builder.fromUri(
                context.getString(
                    com.coolhabit.mocktv.stream.R.string.deep_link_open_tv_stream_value,
                    channelId
                ).toUri()
            ).build()
        )
    }
}
