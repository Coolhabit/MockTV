package com.coolhabit.mocktv.stream.utils

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.coolhabit.mocktv.baseUI.extensions.backgroundColor
import com.coolhabit.mocktv.baseUI.extensions.textColor
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
            if (i == qualityList.lastIndex) {
                newItem.delimiter.visibility = View.GONE
            }
            newItem.root.setOnClickListener {
                onItemClick.invoke(qualityList[i])
            }
            parentView.addView(newItem.root, i)
        }
    }

    private fun isSelectedCheck(view: View, textView: TextView, isSelected: Boolean) {
        if (isSelected) {
            view.backgroundColor(com.coolhabit.mocktv.baseUI.R.color.blue_select)
            textView.textColor(com.coolhabit.mocktv.baseUI.R.color.white)
        } else {
            view.backgroundColor(com.coolhabit.mocktv.baseUI.R.color.white)
            textView.textColor(com.coolhabit.mocktv.baseUI.R.color.black)
        }
    }
}
