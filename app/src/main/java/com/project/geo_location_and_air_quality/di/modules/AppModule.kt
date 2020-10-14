package com.project.geo_location_and_air_quality.di.modules

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.project.geo_location_and_air_quality.base.App
import com.project.geo_location_and_air_quality.db.AppDatabase
import com.project.geo_location_and_air_quality.db.dao.LocationDao
import com.project.geo_location_and_air_quality.networkapi.AddressApiService
import com.project.geo_location_and_air_quality.networkapi.AirApiService
import com.project.geo_location_and_air_quality.util.Config
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ActivityModules::class, ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideAddressApiService(): AddressApiService = Retrofit.Builder()
        .baseUrl(Config.ADDRESS_URL)
        .client(OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AddressApiService::class.java)

    @Singleton
    @Provides
    fun provideAirApiService(): AirApiService = Retrofit.Builder()
        .baseUrl(Config.AIR_QULITY_URL)
        .client(OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AirApiService::class.java)

    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "assigmentdb").build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): LocationDao {
        return db.locationDao()
    }

    @Singleton
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun providesResources(application: App): Resources = application.resources
}