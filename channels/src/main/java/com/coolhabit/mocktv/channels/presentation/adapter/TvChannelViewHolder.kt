package com.coolhabit.mocktv.channels.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.coolhabit.mocktv.baseUI.extensions.load
import com.coolhabit.mocktv.channels.databinding.RvChannelItemBinding
import com.coolhabit.mocktv.domain.entities.TvChannel

class TvChannelViewHolder(private val binding: RvChannelItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TvChannel) {
        with(binding) {
            channelLogo.load(item.channelLogo)
            channelName.text = item.channelName
            currentProgram.text = item.currentProgram
        }
    }
}
