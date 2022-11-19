package com.coolhabit.mocktv.channels.presentation.favs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coolhabit.mocktv.baseUI.presentation.BaseFragment
import com.coolhabit.mocktv.channels.R
import com.coolhabit.mocktv.channels.databinding.FragmentChannelsListBinding
import com.coolhabit.mocktv.channels.databinding.FragmentFavsListBinding

class FavsListFragment : BaseFragment(R.layout.fragment_favs_list) {

    private val viewModel by viewModels<FavsListViewModel>()
    private lateinit var binding: FragmentFavsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavsListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
