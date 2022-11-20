package com.coolhabit.mocktv.ioc.modules

import android.content.Context
import com.coolhabit.mocktv.data.db.DatabaseStorageImpl
import com.coolhabit.mocktv.domain.api.IDatabaseStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StoragesModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): IDatabaseStorage = DatabaseStorageImpl(context)
}
