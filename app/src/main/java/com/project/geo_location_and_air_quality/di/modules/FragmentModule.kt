package com.project.geo_location_and_air_quality.di.modules

import com.project.geo_location_and_air_quality.ui.AFragment
import com.project.geo_location_and_air_quality.ui.BFragment
import com.project.geo_location_and_air_quality.ui.CFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeAFragment(): AFragment
    @ContributesAndroidInjector
    internal abstract fun contributeBFragment(): BFragment
    @ContributesAndroidInjector
    internal abstract fun contributeCFragment(): CFragment
}