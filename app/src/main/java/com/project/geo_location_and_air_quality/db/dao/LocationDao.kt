package com.project.geo_location_and_air_quality.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.geo_location_and_air_quality.db.Model.LocationModel

@Dao
interface LocationDao {

    // 좌표 저장
    @Insert
    fun insertLocationModel(locationModel: LocationModel)
    // 저장된 좌표 정보 획득
    @Query("SELECT * FROM location_model_table")
    fun loadAllInfo(): LiveData<List<LocationModel>>
    // 저장된 좌표 정보 모두 삭제
    @Query("DELETE FROM location_model_table")
    fun deleteAll()
    //저장된 좌표 정보 삭제
    @Delete
    fun delete(locationModel: LocationModel)
}