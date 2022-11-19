package com.coolhabit.mocktv.ioc

import com.coolhabit.mocktv.MockApp
import com.coolhabit.mocktv.data.ioc.RemoteModule
import com.coolhabit.mocktv.ioc.modules.ActivityModule
import com.coolhabit.mocktv.ioc.modules.ApiModule
import com.coolhabit.mocktv.ioc.modules.ApplicationModule
import com.coolhabit.mocktv.ioc.modules.FragmentsModule
import com.coolhabit.mocktv.ioc.modules.NavigationRoutersModule
import com.coolhabit.mocktv.ioc.modules.StoragesModule
import com.coolhabit.mocktv.ioc.modules.UseCasesModule
import com.coolhabit.mocktv.ioc.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        ApplicationModule::class,
        FragmentsModule::class,
        UseCasesModule::class,
        NavigationRoutersModule::class,
        ApiModule::class,
        RemoteModule::class,
        StoragesModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<MockApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: MockApp): ApplicationComponent
    }
}
