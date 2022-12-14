package com.coolhabit.mocktv.data.db

import android.content.Context
import androidx.room.Room
import com.coolhabit.mocktv.data.db.entity.toData
import com.coolhabit.mocktv.data.db.entity.toDomain
import com.coolhabit.mocktv.domain.api.IDatabaseStorage
import com.coolhabit.mocktv.domain.entities.TvChannel

class DatabaseStorageImpl(context: Context) : IDatabaseStorage {

    private val database: AppDatabase =
        Room
            .databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

    override suspend fun addChannelToFavorite(channel: TvChannel) {
        database.channelsDao().insert(channel.toData())
    }

    override suspend fun removeChannelFromFavorite(channel: TvChannel) {
        database.channelsDao().delete(channel.toData())
    }

    override suspend fun getFavoriteChannels(): List<TvChannel> {
        return database.channelsDao().getFavoriteChannels().map {
            it.toDomain()
        }
    }

    override suspend fun getFavoriteChannelsByName(searchPattern: String): List<TvChannel> {
        return database.channelsDao().getFavoriteChannelsByName(searchPattern).map {
            it.toDomain()
        }
    }
}
