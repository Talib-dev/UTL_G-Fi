package com.kura.utl.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kura.utl.R
import com.kura.utl.databinding.ItemProductBinding
import com.kura.utl.databinding.ItemProductListBinding
import com.kura.utl.datalayer.modal.Device


class DashboardAdapter(
    private var list: List<Device>,
    private val callback: (data: Device) -> Unit?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var filterList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VHItem(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_list,
                parent,
                false
            )
        )

    }

    override fun getItemCount() = filterList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VHItem) {
            val data = list[position]
            holder.binding.tvName.text = "${data.sysName} | ${data.serialNo}"
            holder.binding.tvAddress.text = data.location
            holder.binding.tvLastPing.text = data.lastPing
            holder.binding.root.setOnClickListener {
                callback.invoke(data)
            }
        }
    }

    fun update(updatedList: List<Device>) {
        list = updatedList
        filterList = list
    }

    class VHItem(val binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filterList = if (charString.isEmpty()) list else {
                    val filteredList = ArrayList<Device>()
                    list
                        .filter {
                            (it.sysName.contains(constraint!!, true)) or
                                    (it.serialNo.contains(constraint, true)) or
                                    (it.location.contains(constraint, true)) or
                                    (it.model.contains(constraint, true))
                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = filterList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                filterList = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Device>
                notifyDataSetChanged()
            }
        }
    }


}