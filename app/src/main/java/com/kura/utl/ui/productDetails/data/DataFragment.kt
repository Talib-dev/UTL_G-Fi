package com.kura.utl.ui.productDetails.data

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.kura.utl.R
import com.kura.utl.databinding.FragmentGraphBinding
import com.kura.utl.databinding.FragmentMonitringBinding
import com.kura.utl.ui.MainViewModel
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.productDetails.monitoring.MonitoringAdapter
import com.kura.utl.ui.productDetails.monitoring.MonitoringViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DataFragment : BaseFragment<FragmentGraphBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_graph
    private lateinit var mBinding: FragmentGraphBinding
    private lateinit var sharedViewModel: MainViewModel
    private val viewModel: DataViewModel by viewModels()

    private lateinit var adapter: DataAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()

        adapter = DataAdapter(arrayListOf())
        getDataBinding().rvDeviceList.adapter = adapter

        sharedViewModel.serialNo?.let {
            viewModel.getData(it)
            viewModel.data.observe(viewLifecycleOwner) { data ->
                adapter.update(data)

            }

        }

        val calFrom = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calFrom.set(Calendar.YEAR, year)
                calFrom.set(Calendar.MONTH, monthOfYear)
                calFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                mBinding.tvFromTime.text = sdf.format(calFrom.time)
                viewModel.startTime = sdf.format(calFrom.time)

            }
        mBinding.tvFromTime.setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListener,
                calFrom.get(Calendar.YEAR),
                calFrom.get(Calendar.MONTH),
                calFrom.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val calTo = Calendar.getInstance()
        val dateSetListenerTo =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calTo.set(Calendar.YEAR, year)
                calTo.set(Calendar.MONTH, monthOfYear)
                calTo.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "dd.MM.yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                mBinding.tvToTime.text = sdf.format(calTo.time)
                viewModel.endTime = sdf.format(calFrom.time)

            }
        mBinding.tvToTime.setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListenerTo,
                calTo.get(Calendar.YEAR),
                calTo.get(Calendar.MONTH),
                calTo.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}