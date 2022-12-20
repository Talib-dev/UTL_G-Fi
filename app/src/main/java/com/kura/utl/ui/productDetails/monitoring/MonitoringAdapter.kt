package com.kura.utl.ui.productDetails.monitoring

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kura.utl.R
import com.kura.utl.databinding.ItemLiveBinding
import com.kura.utl.databinding.ItemProductBinding
import com.kura.utl.databinding.ItemProductListBinding
import com.kura.utl.datalayer.modal.Device


class MonitoringAdapter(
    private var list: List<LiveModel>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VHItem(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_live,
                parent,
                false
            )
        )

    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VHItem) {
            val data = list[position]
            holder.binding.tvName.text = data.name
            holder.binding.tvName.text = data.value

        }
    }

    fun update(updatedList: List<LiveModel>) {
        list = updatedList
    }

    class VHItem(val binding: ItemLiveBinding) : RecyclerView.ViewHolder(binding.root)



}