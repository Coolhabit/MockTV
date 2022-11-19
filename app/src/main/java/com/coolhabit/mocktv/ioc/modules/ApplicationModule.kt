package com.coolhabit.mocktv.ioc.modules

import android.content.Context
import com.coolhabit.mocktv.MockApp
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideContext(app: MockApp): Context = app.applicationContext
}
