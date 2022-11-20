package com.coolhabit.mocktv.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolhabit.mocktv.data.db.AppDatabase.Companion.DATABASE_NAME
import com.coolhabit.mocktv.data.db.entity.TvChannelDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channel: TvChannelDB)

    @Delete
    suspend fun delete(channel: TvChannelDB)

    @Query("SELECT * FROM $DATABASE_NAME")
    suspend fun getFavoriteChannels(): List<TvChannelDB>

    @Query("SELECT * FROM $DATABASE_NAME WHERE dbChannelName LIKE '%' || :query || '%'")
    suspend fun getFavoriteChannelsByName(query: String): List<TvChannelDB>
}
