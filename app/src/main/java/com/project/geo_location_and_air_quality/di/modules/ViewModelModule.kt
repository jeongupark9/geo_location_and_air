package com.project.geo_location_and_air_quality.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.geo_location_and_air_quality.di.base.ViewModelFactory
import com.project.geo_location_and_air_quality.di.base.ViewModelKey
import com.project.geo_location_and_air_quality.viewmodel.AFragmentViewModel
import com.project.geo_location_and_air_quality.viewmodel.BFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract  class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BFragmentViewModel::class)
    abstract fun bindBViewModel(bViewModel: BFragmentViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AFragmentViewModel::class)
    abstract fun bindAViewModel(aViewModel: AFragmentViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}