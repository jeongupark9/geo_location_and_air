package com.project.geo_location_and_air_quality.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.geo_location_and_air_quality.R
import com.project.geo_location_and_air_quality.base.BaseFragment
import com.project.geo_location_and_air_quality.util.Config
import com.project.geo_location_and_air_quality.util.LocalAndAqiInfo
import kotlinx.android.synthetic.main.c_fragment.*

//Point A와 Point B의 좌표, 주소, 공기질 정보를 보여주는 화면
class CFragment : BaseFragment(){
    private var pointAInfo : LocalAndAqiInfo? = null
    private var pointBInfo : LocalAndAqiInfo? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //AFragment에서 전달해준 좌표, 주소, 공기질 정보
        arguments?.let{
            pointAInfo = it.getSerializable(Config.LOCAL_AND_AQI_A_POINT_VALUE) as LocalAndAqiInfo
            pointBInfo = it.getSerializable(Config.LOCAL_AND_AQI_B_POINT_VALUE) as LocalAndAqiInfo
        }

        return inflater.inflate(R.layout.c_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pointAInfo?.let{
            c_fragment_point_a_latlng_txt.text = "lat/lng: ${it.lat} / ${it.lng}"
            c_fragment_point_a_location_txt.text = "Name: ${it.address}"
            c_fragment_point_a_air_quality_txt.text = "Aqi: ${it.aqi}"
        }
        pointBInfo?.let{
            c_fragment_point_b_latlng_txt.text = "lat/lng: ${it.lat} / ${it.lng}"
            c_fragment_point_b_location_txt.text = "Name: ${it.address}"
            c_fragment_point_b_air_quality_txt.text = "Aqi: ${it.aqi}"
        }
        // Back 버튼 클릭
        c_fragment_back_btn.setOnClickListener {
            if(parentFragmentManager.fragments.size > 1){
                parentFragmentManager.beginTransaction().remove(parentFragmentManager.fragments[parentFragmentManager.fragments.size-1]).commit()
                val preFragment =  parentFragmentManager.fragments[parentFragmentManager.fragments.size-2] as BaseFragment
                preFragment.callback()
            }
        }
    }
}