package com.coolhabit.mocktv.stream.utils

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.coolhabit.mocktv.stream.databinding.ViewQualityItemBinding
import javax.inject.Inject

class QualityItemInflater @Inject constructor() {

    var onItemClick: (QualityItem) -> Unit = {}

    fun itemInflate(parentView: LinearLayout, qualityList: List<QualityItem>) {
        parentView.removeAllViews()
        val itemInflater = LayoutInflater.from(parentView.context)

        for (i in qualityList.indices) {
            val newItem =
                ViewQualityItemBinding.inflate(itemInflater, parentView, false)
            newItem.qualityName.text = qualityList[i].name
            isSelectedCheck(newItem.root, newItem.qualityName, qualityList[i].isSelected)

            newItem.root.setOnClickListener {
                onItemClick.invoke(qualityList[i])
            }
            parentView.addView(newItem.root, i)
        }
    }

    fun isSelectedCheck(view: View, textView: TextView, isSelected: Boolean) {
        if (isSelected) {
            view.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    view.context,
                    com.coolhabit.mocktv.baseUI.R.color.blue_select
                )
            )
            textView.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    com.coolhabit.mocktv.baseUI.R.color.white
                )
            )
        } else {
            view.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    view.context,
                    com.coolhabit.mocktv.baseUI.R.color.white
                )
            )
            textView.setTextColor(
                ContextCompat.getColor(
                    view.context,
                    com.coolhabit.mocktv.baseUI.R.color.black
                )
            )
        }
    }
}
