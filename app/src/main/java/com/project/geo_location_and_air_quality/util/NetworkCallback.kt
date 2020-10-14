package com.project.geo_location_and_air_quality.util

interface NetworkCallback<T> {
    fun onResponse(item: T?)
    fun onFail(message: String?)
}