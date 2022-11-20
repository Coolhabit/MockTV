package com.coolhabit.mocktv.baseUI.model

import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections

sealed class NavCommand {
    data class Navigate(val directions: NavDirections) : NavCommand()

    class Deeplink(val request: NavDeepLinkRequest, val popBackStackTo: Int = -1) : NavCommand()

    class GoBack(val popBackStackTo: Int = -1) : NavCommand()
}
