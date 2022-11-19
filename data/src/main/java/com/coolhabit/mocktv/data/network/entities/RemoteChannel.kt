package com.coolhabit.mocktv.data.network.entities

data class RemoteChannel(
    val address: String,
    val cdn: String,
    val current: RemoteCurrent,
    val drm_status: Int,
    val epg_id: Int,
    val foreign_player: RemoteForeighnPlayer,
    val foreign_player_key: Boolean,
    val hasEpg: Boolean,
    val id: Int,
    val image: String,
    val is_federal: Boolean,
    val is_foreign: Boolean,
    val name_en: String,
    val name_ru: String,
    val number: Int,
    val owner: String,
    val region_code: Int,
    val tz: Int,
    val url: String,
    val url_sound: String,
    val vitrina_events_url: String
)