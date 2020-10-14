package com.project.geo_location_and_air_quality.repository

import androidx.lifecycle.LiveData
import com.project.geo_location_and_air_quality.base.AppExecutors
import com.project.geo_location_and_air_quality.db.AppDatabase
import com.project.geo_location_and_air_quality.db.Model.LocationModel
import com.project.geo_location_and_air_quality.util.LocalAndAqiInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DBRepository  @Inject constructor(
    private val appExecutors: AppExecutors = AppExecutors(),
    private val appDatabase: AppDatabase
){
    // 로컬 DB에 위치 정보 입력
    fun insertDB(localAndAqiInfo: LocalAndAqiInfo) {
        appExecutors.diskIO().execute {
            val locationModel = LocationModel(latitude = localAndAqiInfo.lat,longitude = localAndAqiInfo.lng, address = localAndAqiInfo.address, aqi = localAndAqiInfo.aqi)
            appDatabase.locationDao().insertLocationModel(locationModel)
        }
    }

    // 로컬 DB에 저장된 위치정보들 호출
    fun getLocalAndAqiInfoList() : LiveData<List<LocationModel>> =  appDatabase.locationDao().loadAllInfo()

    // DB에 저장된 모든 위치 정보 삭제
    fun deleteLocationModel(locationModel: LocationModel){
        appExecutors.diskIO().execute {
            appDatabase.locationDao().delete(locationModel)
        }

    }

    // 로컬 DB에 저장된 위치정보 삭제
    fun deleteAllData(){
        appExecutors.diskIO().execute {
            appDatabase.locationDao().deleteAll()
        }
    }
}