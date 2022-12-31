package com.kura.utl.ui.productDetails.control

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.kura.utl.R
import com.kura.utl.databinding.FragmentControlBinding
import com.kura.utl.databinding.FragmentGraphBinding
import com.kura.utl.ui.MainViewModel
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.productDetails.data.DataViewModel


class ControlFragment : BaseFragment<FragmentControlBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_control
    private lateinit var mBinding: FragmentControlBinding
    private lateinit var sharedViewModel: MainViewModel
    private val viewModel: ControlViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        sharedViewModel.serialNo?.let {
            viewModel.getData(it)
        }

        viewModel.data.observe(viewLifecycleOwner){
            mBinding.scFerries.isChecked=it.value
        }
        mBinding.scFerries.setOnCheckedChangeListener{_, isChecked ->
            viewModel.setData(isChecked)
        }




    }

}