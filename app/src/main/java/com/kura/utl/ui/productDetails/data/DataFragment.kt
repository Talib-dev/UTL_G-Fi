package com.kura.utl.ui.productDetails.data

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
    }
}