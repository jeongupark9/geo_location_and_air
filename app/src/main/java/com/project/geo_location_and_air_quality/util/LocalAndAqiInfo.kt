package com.project.geo_location_and_air_quality.util

import java.io.Serializable

// 좌표, address, airqulty를 저장하는 클래스
data class LocalAndAqiInfo(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var address: String,
    var aqi: Int = 0
) : Serializable