package com.project.geo_location_and_air_quality.viewmodel

import androidx.lifecycle.ViewModel
import com.project.geo_location_and_air_quality.util.LocalAndAqiInfo
import javax.inject.Inject

class AFragmentViewModel  @Inject constructor(
) : ViewModel() {

    private var localAndAqiInfo_A : LocalAndAqiInfo? = null
    private var localAndAqiInfo_B : LocalAndAqiInfo? = null

    fun clearPoints(){
        localAndAqiInfo_A = null
        localAndAqiInfo_B = null
    }

    fun setAPoint(laa : LocalAndAqiInfo){
        localAndAqiInfo_A = laa
    }

    fun getAPoint() = localAndAqiInfo_A

    fun setBPoint(laa: LocalAndAqiInfo){
        localAndAqiInfo_B = laa
    }

    fun getBPoint() = localAndAqiInfo_B
}