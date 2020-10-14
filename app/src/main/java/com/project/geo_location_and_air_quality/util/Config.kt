package com.project.geo_location_and_air_quality.util

import java.io.Serializable

class Config {

    companion object {
        const val ADDRESS_URL = "https://api.bigdatacloud.net/data/"
        const val AIR_QULITY_URL = "https://api.waqi.info/"
        const val AIT_QULITY_TOKEN = "6a75036e8e09ede1b4d85fe8c71f5e41792bf196"
        val SET_TYPE = "SET_TYPE"
        val LOCAL_AND_AQI_VALUE = "LOCAL_AND_AQI_VALUE"
        val LOCAL_AND_AQI_A_POINT_VALUE = "LOCAL_AND_AQI_A_POINT_VALUE"
        val LOCAL_AND_AQI_B_POINT_VALUE = "LOCAL_AND_AQI_B_POINT_VALUE"
    }
    enum class SetType : Serializable{
        POINT_A, POINT_B
    }

}