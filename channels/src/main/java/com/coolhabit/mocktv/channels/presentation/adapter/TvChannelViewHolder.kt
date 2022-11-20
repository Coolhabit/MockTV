package com.coolhabit.mocktv.channels.presentation.adapter

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.coolhabit.mocktv.baseUI.extensions.load
import com.coolhabit.mocktv.channels.R
import com.coolhabit.mocktv.channels.databinding.RvChannelItemBinding
import com.coolhabit.mocktv.domain.entities.TvChannel
import com.google.android.material.imageview.ShapeableImageView

class TvChannelViewHolder(
    private val binding: RvChannelItemBinding,
    private val onFavClick: (TvChannel) -> Unit,
    private val onCardClick: (Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            onCardClick.invoke(bindingAdapterPosition)
        }
    }

    fun bind(item: TvChannel) {
        with(binding) {
            channelLogo.load(item.channelLogo)
            channelName.text = item.channelName
            currentProgram.text = item.currentProgram
            favCheck(favBtn, item.isFavorite)
            favBtn.setOnClickListener {
                onFavClick.invoke(item)
            }
        }
    }

    private fun favCheck(button: ShapeableImageView, inFavorites: Boolean) {
        if (inFavorites) {
            button.setImageResource(com.coolhabit.mocktv.baseUI.R.drawable.ic_star)
        } else {
            button.setImageResource(com.coolhabit.mocktv.baseUI.R.drawable.ic_star_unselected)
        }
    }
}
