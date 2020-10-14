package com.project.geo_location_and_air_quality.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.geo_location_and_air_quality.db.Model.LocationModel
import com.project.geo_location_and_air_quality.db.dao.LocationDao

@Database(entities = [LocationModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}