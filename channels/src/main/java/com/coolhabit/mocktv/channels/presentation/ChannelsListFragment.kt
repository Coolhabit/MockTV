package com.coolhabit.mocktv.channels.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.coolhabit.mocktv.baseUI.adapter.ItemDecoration
import com.coolhabit.mocktv.baseUI.presentation.BaseFragment
import com.coolhabit.mocktv.channels.R
import com.coolhabit.mocktv.channels.databinding.FragmentChannelsListBinding
import com.coolhabit.mocktv.channels.presentation.adapter.TvChannelAdapter
import com.coolhabit.mocktv.channels.presentation.base.ChannelsBaseFragmentDirections
import com.coolhabit.mocktv.channels.presentation.extensions.toUiModel
import com.coolhabit.mocktv.baseUI.model.TvChannelUI
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ChannelsListFragment : BaseFragment(R.layout.fragment_channels_list) {

    private val viewModel by viewModels<ChannelsListViewModel>()
    private lateinit var binding: FragmentChannelsListBinding

    @Inject
    lateinit var channelsAdapter: TvChannelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initContent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChannelsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvChannels.apply {
            adapter = channelsAdapter
            itemAnimator = null
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                ItemDecoration(
                    context,
                    com.coolhabit.mocktv.baseUI.R.dimen.size_6,
                    com.coolhabit.mocktv.baseUI.R.dimen.size_6,
                    com.coolhabit.mocktv.baseUI.R.dimen.size_6,
                    com.coolhabit.mocktv.baseUI.R.dimen.size_6,
                )
            )
        }

        channelsAdapter.onCardClick = {
            toStream(it.toUiModel())
        }
        channelsAdapter.onFavClick = {
            viewModel.changeFavStatus(it)
        }

        submitList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.initContent()
    }

    private fun submitList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadChannels.collect { list ->
                channelsAdapter.submitList(list)
            }
        }
    }

    private fun toStream(channel: TvChannelUI) {
        val directions = ChannelsBaseFragmentDirections.openTvStream(channel)
        findNavController().navigate(directions)
    }
}
