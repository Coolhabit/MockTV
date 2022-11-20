package com.coolhabit.mocktv.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coolhabit.mocktv.data.db.AppDatabase.Companion.DATABASE_NAME
import com.coolhabit.mocktv.domain.entities.TvChannel

@Entity(tableName = DATABASE_NAME)
data class TvChannelDB(
    @PrimaryKey
    @ColumnInfo(name = "dbChannelId") val dbChannelId: Int,
    @ColumnInfo(name = "dbChannelName") val dbChannelName: String,
    @ColumnInfo(name = "dbChannelLogo") val dbChannelLogo: String,
    @ColumnInfo(name = "dbChannelCurrent") val dbChannelCurrent: String,
    @ColumnInfo(name = "dbChannelUrl") val dbChannelUrl: String,
)

fun TvChannel.toData() = TvChannelDB(
    dbChannelId = this.channelId,
    dbChannelName = this.channelName,
    dbChannelLogo = this.channelLogo,
    dbChannelCurrent = this.currentProgram,
    dbChannelUrl = this.streamUrl,
)

fun TvChannelDB.toDomain() = TvChannel(
    channelId = this.dbChannelId,
    channelName = this.dbChannelName,
    channelLogo = this.dbChannelLogo,
    currentProgram = this.dbChannelCurrent,
    streamUrl = this.dbChannelUrl,
    isFavorite = true,
)
