package com.coolhabit.mocktv.channels.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.coolhabit.mocktv.channels.databinding.RvChannelItemBinding
import com.coolhabit.mocktv.domain.entities.TvChannel
import javax.inject.Inject

class TvChannelAdapter @Inject constructor() : ListAdapter<TvChannel, TvChannelViewHolder>(TvChannelDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvChannelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvChannelItemBinding.inflate(inflater, parent, false)
        return TvChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvChannelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TvChannelDiffUtils: DiffUtil.ItemCallback<TvChannel>() {

        override fun areItemsTheSame(oldItem: TvChannel, newItem: TvChannel): Boolean {
            return oldItem.channelId == newItem.channelId
        }

        override fun areContentsTheSame(oldItem: TvChannel, newItem: TvChannel): Boolean {
            return oldItem == newItem
        }
    }
}
