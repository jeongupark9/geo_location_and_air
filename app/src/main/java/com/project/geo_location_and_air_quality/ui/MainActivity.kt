package com.project.geo_location_and_air_quality.ui

import android.os.Bundle
import android.util.Log
import com.project.geo_location_and_air_quality.R
import com.project.geo_location_and_air_quality.base.BaseActivity

class MainActivity : BaseActivity(){
    private val TAG : String  = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, AFragment() )
            .commit()
    }

    override fun onBackPressed() {
        Log.d(TAG,"onBackPressed")
        if(supportFragmentManager.fragments.size > 1){
            supportFragmentManager.beginTransaction().remove(supportFragmentManager.fragments[supportFragmentManager.fragments.size-1]).commit()
        }else{
            super.onBackPressed()
        }
    }
}