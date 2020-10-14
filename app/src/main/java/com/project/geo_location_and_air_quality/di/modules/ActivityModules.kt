package com.project.geo_location_and_air_quality.di.modules
import com.project.geo_location_and_air_quality.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [FragmentModule::class])
abstract class ActivityModules {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity
}