package com.project.geo_location_and_air_quality.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.project.geo_location_and_air_quality.R
import com.project.geo_location_and_air_quality.base.BaseFragment
import com.project.geo_location_and_air_quality.util.Config
import com.project.geo_location_and_air_quality.util.Config.SetType
import com.project.geo_location_and_air_quality.util.LocalAndAqiInfo
import com.project.geo_location_and_air_quality.viewmodel.AFragmentViewModel
import kotlinx.android.synthetic.main.a_fragment.*

/**
 *  좌표 표시 Fragment
 */
class AFragment : BaseFragment() {
    private lateinit var viewModel: AFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.a_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AFragmentViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        a_fragment_set_a_btn.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, BFragment(SetType.POINT_A))
                .commit()
        }
        a_fragment_set_b_btn.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, BFragment(SetType.POINT_B))
                .commit()
        }
        a_fragment_clear_btn.setOnClickListener {
            clearData()
        }
    }
    private fun clearData(){
        viewModel.clearPoints()
        a_fragment_point_a_latlng_txt.text = ""
        a_fragment_point_b_latlng_txt.text = ""
    }
    // BFragment에서 돌아 올 때 호출 되는 함수
    override fun callback(resultbundle: Bundle?) {

        if(resultbundle != null){
            val localAndAqiInfo = resultbundle.getSerializable(Config.LOCAL_AND_AQI_VALUE) as LocalAndAqiInfo
            val setType = resultbundle.getSerializable(Config.SET_TYPE) as SetType

            if(setType == SetType.POINT_A){
                a_fragment_point_a_latlng_txt.text = "${localAndAqiInfo.lat} / ${localAndAqiInfo.lng}"
                viewModel.setAPoint(localAndAqiInfo)
            }else{
                a_fragment_point_b_latlng_txt.text = "${localAndAqiInfo.lat} / ${localAndAqiInfo.lng}"
                viewModel.setBPoint(localAndAqiInfo)
            }

            if (a_fragment_point_a_latlng_txt.text.isNotEmpty() && a_fragment_point_b_latlng_txt.text.isNotEmpty()){
                val bundle = Bundle()
                bundle.putSerializable(Config.LOCAL_AND_AQI_A_POINT_VALUE, viewModel.getAPoint())
                bundle.putSerializable(Config.LOCAL_AND_AQI_B_POINT_VALUE, viewModel.getBPoint())
                val fragment = CFragment()
                fragment.arguments = bundle
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container,fragment )
                    .commit()
            }

        }

    }

    override fun callback() {
        clearData()
    }
}