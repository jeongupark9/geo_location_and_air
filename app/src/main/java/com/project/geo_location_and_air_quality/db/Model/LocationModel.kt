package com.project.geo_location_and_air_quality.db.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_model_table")
data class LocationModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var latitude : Double?,
    var longitude : Double?,
    var address : String,
    var aqi : Int

)