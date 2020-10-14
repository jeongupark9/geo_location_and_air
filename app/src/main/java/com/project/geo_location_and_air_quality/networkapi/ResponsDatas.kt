package com.project.geo_location_and_air_quality.networkapi
//위치 정보 데이터
data class Address(var latitude : Double, var longitude: Double, var continent : String, var city: String , var localityInfo : localityInfo)
//위치 정보 데이터 중 위치 상제 정보 리스트
data class localityInfo(var administrative : List<administrativeData>)
//위치 정보 데이터 중 위치 상제 정보
data class administrativeData(var order:Double, val adminLevel : Double, var name: String, var description: String, var isoName: String )
//공기 질 정보 응답 데이터
data class AirQualityResponse(var data : AirQuality)
//공기 질 정보 값
data class AirQuality(var aqi : Int)
