package com.coolhabit.mocktv.channels.presentation.favs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.coolhabit.mocktv.baseUI.adapter.ItemDecoration
import com.coolhabit.mocktv.baseUI.presentation.BaseFragment
import com.coolhabit.mocktv.channels.R
import com.coolhabit.mocktv.channels.databinding.FragmentChannelsListBinding
import com.coolhabit.mocktv.channels.presentation.adapter.TvChannelAdapter
import com.coolhabit.mocktv.channels.presentation.base.ChannelsBaseFragment
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FavsListFragment : BaseFragment(R.layout.fragment_channels_list) {

    private val viewModel by viewModels<FavsListViewModel>()
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
        val parentFrag = requireParentFragment() as ChannelsBaseFragment

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

        with(parentFrag.binding) {
            searchBar.apply {
                searchInput.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        viewModel.performSearch(searchInput.text.toString())
                        true
                    } else {
                        false
                    }
                }

                searchInput.doOnTextChanged { _, _, _, _ ->
                    viewModel.performSearch(searchInput.text.toString())
                }

                searchInputLayout.setEndIconOnClickListener {
                    searchInput.text?.clear()
                    viewModel.initContent()
                }
            }
        }

        channelsAdapter.onCardClick = {
            Toast.makeText(requireContext(), "go to channel ${it.channelName}", Toast.LENGTH_SHORT).show()
        }
        channelsAdapter.onFavClick = {
            viewModel.removeChannelFromFav(it)
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
}
