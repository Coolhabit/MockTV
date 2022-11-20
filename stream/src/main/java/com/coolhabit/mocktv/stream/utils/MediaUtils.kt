package com.coolhabit.mocktv.stream.utils

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides

const val AUTO = "Auto"

fun DefaultTrackSelector.generateQualityList(currentItemName: String?): MutableList<QualityItem> {

    val trackOverrideList = mutableListOf<QualityItem>()

    val renderTrack = this.currentMappedTrackInfo
    val renderCount = renderTrack?.rendererCount ?: 0
    for (rendererIndex in 0 until renderCount) {
        if (isSupportedFormat(renderTrack, rendererIndex)) {
            val trackGroupType = renderTrack?.getRendererType(rendererIndex)
            val trackGroups = renderTrack?.getTrackGroups(rendererIndex)
            val trackGroupsCount = trackGroups?.length
            trackGroupsCount?.let {
                if (trackGroupType == C.TRACK_TYPE_VIDEO) {
                    for (groupIndex in 0 until trackGroupsCount) {
                        val videoQualityTrackCount = trackGroups[groupIndex].length
                        for (trackIndex in 0 until videoQualityTrackCount) {
                            val isTrackSupported = renderTrack.getTrackSupport(
                                rendererIndex,
                                groupIndex,
                                trackIndex
                            ) == C.FORMAT_HANDLED
                            if (isTrackSupported) {
                                val track = trackGroups[groupIndex]
                                val trackName =
                                    "${track.getFormat(trackIndex).height}p"
                                val trackBuilder =
                                    TrackSelectionOverrides.Builder()
                                        .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
                                        .addOverride(
                                            TrackSelectionOverrides.TrackSelectionOverride(
                                                track,
                                                listOf(trackIndex)
                                            )
                                        )
                                trackOverrideList.add(
                                    QualityItem(
                                        trackName,
                                        trackBuilder,
                                        trackName == currentItemName
                                    )
                                )
                            }
                        }
                    }
                    trackOverrideList.add(autoTrack(currentItemName))
                }
            }

        }
    }
    return trackOverrideList
}

fun isSupportedFormat(
    mappedTrackInfo: MappingTrackSelector.MappedTrackInfo?,
    rendererIndex: Int
): Boolean {
    val trackGroupArray = mappedTrackInfo?.getTrackGroups(rendererIndex)
    return if (trackGroupArray?.length == 0) {
        false
    } else mappedTrackInfo?.getRendererType(rendererIndex) == C.TRACK_TYPE_VIDEO || mappedTrackInfo?.getRendererType(
        rendererIndex
    ) == C.TRACK_TYPE_AUDIO || mappedTrackInfo?.getRendererType(rendererIndex) == C.TRACK_TYPE_TEXT
}

fun autoTrack(currentItemName: String?) = QualityItem(
    AUTO,
    TrackSelectionOverrides.Builder()
        .clearOverridesOfType(C.TRACK_TYPE_VIDEO),
    (currentItemName == null || currentItemName == AUTO)
)
