package com.kura.utl.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kura.utl.R
import com.kura.utl.databinding.ItemProductBinding
import com.kura.utl.datalayer.modal.Device


class DashboardAdapter(
    var list: ArrayList<Device>,
    private val callback: (data: Device) -> Unit?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var filterList: ArrayList<Device> = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VHItem(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product,
                parent,
                false
            )
        )

    }

    override fun getItemCount() = filterList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VHItem) {
            val data = list[position]
            holder.binding.tvProductId.text = data.serialNo
            holder.binding.tvProductName.text = data.sysName
            holder.binding.tvLocation.text = data.location
        }
    }


    class VHItem(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}