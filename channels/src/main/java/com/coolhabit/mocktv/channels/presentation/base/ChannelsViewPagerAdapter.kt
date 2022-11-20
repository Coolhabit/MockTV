package com.coolhabit.mocktv.channels.presentation.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.coolhabit.mocktv.baseUI.extensions.PAGER_COUNT
import com.coolhabit.mocktv.channels.presentation.list.ChannelsListFragment
import com.coolhabit.mocktv.channels.presentation.base.ChannelsBaseFragment.Companion.ALL_TAB
import com.coolhabit.mocktv.channels.presentation.base.ChannelsBaseFragment.Companion.FAV_TAB
import com.coolhabit.mocktv.channels.presentation.favs.FavsListFragment

class ChannelsViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = PAGER_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            ALL_TAB -> ChannelsListFragment()
            FAV_TAB -> FavsListFragment()
            else -> throw IllegalStateException()
        }
    }
}
