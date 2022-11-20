package com.coolhabit.mocktv.stream.utils

import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides

data class QualityItem(
    val name: String,
    val selectionOverride: TrackSelectionOverrides.Builder,
    var isSelected: Boolean = false
)
