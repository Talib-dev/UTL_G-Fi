package com.kura.utl.ui.productDetails.data

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kura.utl.R
import com.kura.utl.databinding.ItemDataBinding
import com.kura.utl.databinding.ItemLiveBinding
import com.kura.utl.databinding.ItemProductBinding
import com.kura.utl.databinding.ItemProductListBinding
import com.kura.utl.datalayer.modal.DataKey
import com.kura.utl.datalayer.modal.Device
import com.kura.utl.datalayer.modal.DeviceDataModel


class DataAdapter(
    private var list: List<DeviceDataModel>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VHItem(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_data,
                parent,
                false
            )
        )

    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VHItem) {
            val data = list[position]
            holder.binding.tvVolt.text = data.InputAC?.Volt?.BR
            holder.binding.tvBattery.text = data.Battery?.Capacity
            holder.binding.tvSolar.text = data.Solar?.Volt
            holder.binding.tvLoad.text = data.OutputAC?.LoadPerc?.BR

        }
    }

    fun update(updatedList: List<DeviceDataModel>) {
        list = updatedList
        notifyDataSetChanged()
    }

    class VHItem(val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root)



}