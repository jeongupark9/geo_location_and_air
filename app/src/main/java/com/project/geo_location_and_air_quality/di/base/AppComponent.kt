package com.project.geo_location_and_air_quality.di.base

import com.project.geo_location_and_air_quality.base.App
import com.project.geo_location_and_air_quality.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class,AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App>  {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

}