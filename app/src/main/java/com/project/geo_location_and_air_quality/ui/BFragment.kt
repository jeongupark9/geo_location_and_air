package com.project.geo_location_and_air_quality.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.geo_location_and_air_quality.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.project.geo_location_and_air_quality.base.BaseFragment
import com.project.geo_location_and_air_quality.db.Model.LocationModel
import com.project.geo_location_and_air_quality.util.Config
import com.project.geo_location_and_air_quality.util.Config.SetType
import com.project.geo_location_and_air_quality.util.LocalAndAqiInfo
import com.project.geo_location_and_air_quality.util.LocalInfo
import com.project.geo_location_and_air_quality.viewmodel.BFragmentViewModel
import kotlinx.android.synthetic.main.b_fragment.*

class BFragment(private var setType: SetType) : BaseFragment(), OnMapReadyCallback {
    private val TAG = "BFragment"
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: BFragmentViewModel
    private var isGetAddressInfo: Boolean = false
    private var isGetAirQuality: Boolean = false
    private var current_lat : Double = 0.0
    private var current_lng : Double = 0.0
    private var current_address : String = ""
    private var current_aqi : Int = 0
    private var localAndAqiInfoList : List<LocationModel> = ArrayList<LocationModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.b_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Current Type: $setType")
        val mapView = childFragmentManager
            .findFragmentById(R.id.b_fragment_map) as SupportMapFragment
        mapView.getMapAsync(this)

        //recycler view setting
        b_fragment_recyclerview.layoutManager = LinearLayoutManager(context)
        b_fragment_recyclerview.adapter = LocalAndAqiAdapter(localAndAqiInfoList,itemClickListener,viewModel, context!!)

        // Set Btn click
        b_fragment_set_btn.setOnClickListener {
            if(parentFragmentManager.fragments.size > 1){
                viewModel.insertLocationData( LocalAndAqiInfo(current_lat,current_lng,current_address,current_aqi),setType)
                parentFragmentManager.beginTransaction().remove(parentFragmentManager.fragments[parentFragmentManager.fragments.size-1]).commit()
                val preFragment =  parentFragmentManager.fragments[parentFragmentManager.fragments.size-2] as BaseFragment
                preFragment.callback(setResultBundle())
            }
        }
        // zoom out
        b_fragment_zoom_out.setOnClickListener {
            mMap.moveCamera(CameraUpdateFactory.zoomOut())
        }
        // zoom in
        b_fragment_zoom_in.setOnClickListener {
            mMap.moveCamera(CameraUpdateFactory.zoomIn())
        }

        //history btn click
        b_fragment_show_history.setOnClickListener {
            if( b_fragment_history_layout.visibility != View.VISIBLE ){
                b_fragment_history_layout.visibility = View.VISIBLE
            }else{
                b_fragment_history_layout.visibility = View.INVISIBLE
            }
        }
        //remove all btn click(remove all btn은 history layout에 포함됨)
        b_fragment_remove_all_history_data.setOnClickListener {
            viewModel.deleteAllData()
        }
        //주소 정보 관련 LiveData
        viewModel.getLocalInfo().observe(viewLifecycleOwner,
            Observer<LocalInfo> { t ->
                Log.d(TAG, "address: ${t?.address}")
                isGetAddressInfo = true
                current_lat = t.lat
                current_lng = t.lng
                b_fragment_map_info_address_txt.text =  "Name: ${t.address}"
                current_address = t.address
                if (isGetAddressInfo && isGetAirQuality) {
                    activity!!.runOnUiThread {
                        b_fragment_map_info_layout.visibility = View.VISIBLE
                        b_fragment_set_btn.isClickable = true
                        b_fragment_set_btn.alpha = 1f
                    }
                }
            })
        //공기 질 관련 LiveData
        viewModel.getAirQuality().observe(viewLifecycleOwner,
            Observer<Int> { t ->
                Log.d(TAG, "airQuality: $t")
                isGetAirQuality = true
                current_aqi = t
                b_fragment_map_info_aqi_txt.text = "Aqi: $t"
                if (isGetAddressInfo && isGetAirQuality) {
                    activity!!.runOnUiThread {
                        b_fragment_map_info_layout.visibility = View.VISIBLE
                        b_fragment_set_btn.isClickable = true
                        b_fragment_set_btn.alpha = 1f
                    }
                }
            })
        // 로컬 DB에 저장된 좌표 관련 LiveData
        viewModel.getLocalInfoList().observe(viewLifecycleOwner, Observer {
            localAndAqiInfoList = it
            b_fragment_recyclerview.adapter = LocalAndAqiAdapter(localAndAqiInfoList,itemClickListener,viewModel, context!!)
            b_fragment_recyclerview.adapter?.notifyDataSetChanged()
        })
    }

    // 좌표 History에서 item 선택 시 좌표 이동
    private val itemClickListener : View.OnClickListener = View.OnClickListener { p0 ->
        p0?.let {
            val position = b_fragment_recyclerview.getChildAdapterPosition(it)
            val locationModel = localAndAqiInfoList[position]
            val latLng = LatLng(locationModel.latitude!!,locationModel.longitude!! )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            val msg = Message()
            msg.obj = latLng
            mHandler.sendMessageDelayed(msg, 0)
            b_fragment_history_layout.visibility = View.GONE
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BFragmentViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disposeAll()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val startPoint = LatLng(37.5048318, 127.0326396)
        val msg = Message()
        msg.obj = startPoint
        mHandler.sendMessageDelayed(msg, 500)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,10f))
        mMap.setOnCameraMoveListener {
            isGetAddressInfo = false
            isGetAirQuality = false
            b_fragment_set_btn.isClickable = false
            b_fragment_set_btn.alpha = 0.5f
            b_fragment_map_info_layout.visibility = View.GONE
            mHandler.removeMessages(0)
            val cameraPos: CameraPosition = mMap.cameraPosition
            val msg = Message()
            msg.obj = cameraPos
            mHandler.sendMessageDelayed(msg, 500)
        }
    }
    // map 이동 후 0.5초 있다 과련 정보 요청 하도록 Handler 사용
    private var mHandler: Handler = Handler(Looper.getMainLooper()) {
        if (it.obj is CameraPosition) {
            val position = it.obj as CameraPosition
            viewModel.requestPointInfo(position.target.latitude, position.target.longitude)
        } else if (it.obj is LatLng) {
            val position = it.obj as LatLng
            viewModel.requestPointInfo(position.latitude, position.longitude)
        }
        false
    }

    // Set 버튼 눌러 BFragment를 닫고 AFragment로 이동할 때 전달 할 Bundle 생성
    private fun setResultBundle() : Bundle = Bundle().apply {
        this.putSerializable(Config.SET_TYPE,setType)
        this.putSerializable(Config.LOCAL_AND_AQI_VALUE, LocalAndAqiInfo(current_lat,current_lng,current_address,current_aqi))
    }


}