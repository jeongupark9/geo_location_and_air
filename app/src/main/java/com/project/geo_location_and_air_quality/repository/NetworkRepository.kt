package com.project.geo_location_and_air_quality.repository

import android.annotation.SuppressLint
import android.os.Looper
import com.project.geo_location_and_air_quality.networkapi.Address
import com.project.geo_location_and_air_quality.networkapi.AddressApiService
import com.project.geo_location_and_air_quality.networkapi.AirApiService
import com.project.geo_location_and_air_quality.networkapi.AirQualityResponse
import com.project.geo_location_and_air_quality.util.Config
import com.project.geo_location_and_air_quality.util.NetworkCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val addressApiService: AddressApiService,
    private val airApiService: AirApiService
) {
    // 주소 정보 요청
    fun getAddress(lat: Double, lng: Double, callback: NetworkCallback<Address>) = addressApiService.getAddress(lat, lng).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.from(Looper.myLooper()))
            .subscribe({ address ->
                callback.onResponse(address)
            }, { e ->
                callback.onFail(e.message)
            })


    //공기 정보 요청
    fun getAirQuality(lat: Double, lng: Double, callback: NetworkCallback<AirQualityResponse>) = airApiService.getAirQuality(lat, lng, Config.AIT_QULITY_TOKEN)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.from(Looper.myLooper()))
            .subscribe({ airQualityResponse ->
                callback.onResponse(airQualityResponse)
            }, { e ->
                callback.onFail(e.message)
            })
}