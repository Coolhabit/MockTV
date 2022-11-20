package com.coolhabit.mocktv.data.network.entities

data class RemoteCurrent(
    val cdnvideo: Int,
    val desc: String,
    val rating: Int,
    val timestart: Int,
    val timestop: Int,
    val title: String
)
