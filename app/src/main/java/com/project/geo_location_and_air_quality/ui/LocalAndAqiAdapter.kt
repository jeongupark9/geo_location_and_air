package com.project.geo_location_and_air_quality.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.geo_location_and_air_quality.R
import com.project.geo_location_and_air_quality.db.Model.LocationModel
import com.project.geo_location_and_air_quality.viewmodel.BFragmentViewModel
import kotlinx.android.synthetic.main.cell_location_history.view.*

// DB에 저장된 정보를 리사이클러 뷰로 보여주기 위한 Adapter와 ViewHolder
class LocalAndAqiAdapter(
    var list: List<LocationModel>,
    var clickListener: View.OnClickListener,
    var viewModel: BFragmentViewModel,
    var context: Context
) :
    RecyclerView.Adapter<LocalAndAqiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalAndAqiViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.cell_location_history, null)
        view.setOnClickListener(clickListener)
        return LocalAndAqiViewHolder(view,viewModel)
    }

    override fun onBindViewHolder(holder: LocalAndAqiViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size

}


class LocalAndAqiViewHolder(var view: View, var viewModel: BFragmentViewModel) : RecyclerView.ViewHolder(view) {
    private lateinit var locationModel : LocationModel
    fun setData(locationModel : LocationModel){
        this.locationModel =  locationModel
        view.cell_latlng_txt.text  = "lat/lng: ${locationModel.latitude} / ${locationModel.longitude}"
        view.cell_address_txt.text = "Name: ${locationModel.address}"
        view.cell_aqi_txt.text = "Aqi: ${locationModel.aqi}"
        view.cell_remove_data_btn.setOnClickListener {
            viewModel.deleteLocalInfo(locationModel)
        }
    }
}