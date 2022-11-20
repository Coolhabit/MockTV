package com.coolhabit.mocktv.baseUI.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvChannelUI(
    val id: Int,
    val name: String,
    val logo: String,
    val program: String,
    val url: String
) : Parcelable
