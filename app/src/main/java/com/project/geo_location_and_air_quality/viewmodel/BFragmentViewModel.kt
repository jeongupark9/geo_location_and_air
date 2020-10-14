package com.project.geo_location_and_air_quality.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.geo_location_and_air_quality.db.Model.LocationModel
import com.project.geo_location_and_air_quality.networkapi.Address
import com.project.geo_location_and_air_quality.networkapi.AirQualityResponse
import com.project.geo_location_and_air_quality.repository.DBRepository
import com.project.geo_location_and_air_quality.repository.NetworkRepository
import com.project.geo_location_and_air_quality.util.Config
import com.project.geo_location_and_air_quality.util.LocalAndAqiInfo
import com.project.geo_location_and_air_quality.util.LocalInfo
import com.project.geo_location_and_air_quality.util.NetworkCallback
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class BFragmentViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dbRepository: DBRepository,
    private val context: Context
) : ViewModel() {
    private var localInfo: LocalInfo = LocalInfo()
    private val localInfoLive: MutableLiveData<LocalInfo> = MutableLiveData<LocalInfo>()
    private val airQualityLive: MutableLiveData<Int> = MutableLiveData<Int>()
    private var locationModels: LiveData<List<LocationModel>> =
        dbRepository.getLocalAndAqiInfoList()
    private var mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    fun getLocalInfo() = localInfoLive
    fun getAirQuality() = airQualityLive

    // 좌표의 정보(주소, 공기질) 요청
    fun requestPointInfo(lat: Double, lng: Double) {
        val addressDisposable =
            networkRepository.getAddress(lat, lng, object : NetworkCallback<Address> {
                override fun onResponse(item: Address?) {
                    item?.let { item ->
                        val list = item.localityInfo.administrative
                        list?.let{
                            localInfo.apply {
                                this.lat = item.latitude
                                this.lng = item.longitude
                                val sortList = list.sortedBy { it.order }
                                this.address =
                                    "${sortList[sortList.size - 2].name} ${sortList[sortList.size - 1].name}"
                            }
                            localInfoLive.value = localInfo
                        }

                    }
                }

                override fun onFail(message: String?) {
                    netWorkRequestFail("Address Info", message)
                }

            })
        val aqiDisposable =
            networkRepository.getAirQuality(lat, lng, object : NetworkCallback<AirQualityResponse> {
                override fun onResponse(item: AirQualityResponse?) {
                    item?.let {
                        val aqi = it.data.aqi
                        airQualityLive.value = aqi
                    }
                }

                override fun onFail(message: String?) {
                    netWorkRequestFail("Aqi", message)
                }

            })
        mCompositeDisposable.addAll(addressDisposable, aqiDisposable)
    }

    private fun netWorkRequestFail(type: String, message: String?) {
        if (message != null) {
            Toast.makeText(context, "$type request fail\nmessage: $message", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(context, "$type request fail", Toast.LENGTH_LONG).show()
        }
    }

    fun insertLocationData(localAndAqiInfo: LocalAndAqiInfo, setType: Config.SetType) {
        dbRepository.insertDB(localAndAqiInfo)
    }

    fun getLocalInfoList() = locationModels

    fun deleteLocalInfo(locationModel: LocationModel) {
        dbRepository.deleteLocationModel(locationModel)
        locationModels = dbRepository.getLocalAndAqiInfoList()
    }

    fun deleteAllData() {
        dbRepository.deleteAllData()
        locationModels = dbRepository.getLocalAndAqiInfoList()
    }

    fun disposeAll() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }
}