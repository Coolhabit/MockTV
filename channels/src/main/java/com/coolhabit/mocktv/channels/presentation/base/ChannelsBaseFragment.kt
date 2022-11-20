package com.coolhabit.mocktv.channels.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.coolhabit.mocktv.baseUI.presentation.BaseFragment
import com.coolhabit.mocktv.channels.R
import com.coolhabit.mocktv.channels.databinding.FragmentChannelsBaseBinding
import com.google.android.material.tabs.TabLayoutMediator

class ChannelsBaseFragment : BaseFragment(R.layout.fragment_channels_base) {

    companion object {
        const val ALL_TAB = 0
        const val FAV_TAB = 1
    }

    lateinit var binding: FragmentChannelsBaseBinding
    private val viewModel by viewModels<ChannelsBaseViewModel>()
    private lateinit var adapter: ChannelsViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChannelsBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ChannelsViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                ALL_TAB -> tab.text = context?.getString(R.string.all_tab)
                FAV_TAB -> tab.text = context?.getString(R.string.fav_tab)
            }
        }.attach()
    }
}
