package com.project.geo_location_and_air_quality.networkapi

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface AirApiService {
    @Headers("Accept: application/json")
    @GET("feed/geo:{latitude};{longitude}/")
    fun getAirQuality(@Path("latitude") latitude: Double,
                     @Path("longitude") longitude: Double,
                     @Query("token") token: String) : Single<AirQualityResponse>
}

interface AddressApiService {
    @Headers("Accept: application/json")
    @GET("reverse-geocode-client?")
    fun getAddress(@Query("latitude") latitude: Double,
                   @Query("longitude") longitude: Double,
                   @Query("localityLanguage") localityLanguage: String = "ko") : Single<Address>
}