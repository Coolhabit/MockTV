package com.coolhabit.mocktv.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coolhabit.mocktv.data.db.AppDatabase.Companion.DATABASE_VERSION
import com.coolhabit.mocktv.data.db.dao.MockDao
import com.coolhabit.mocktv.data.db.entity.TvChannelDB

@Database(entities = [TvChannelDB::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun channelsDao(): MockDao

    companion object {
        const val DATABASE_NAME = "mocktv_db"
        const val DATABASE_VERSION = 1
    }
}
