package com.kura.utl.ui.productDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.kura.utl.R
import com.kura.utl.databinding.FragmentLoginBinding
import com.kura.utl.databinding.FragmentProductDetailsBinding
import com.kura.utl.ui.MainViewModel
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.productDetails.monitoring.MonitoringFragment
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems


class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_product_details
    private lateinit var mBinding: FragmentProductDetailsBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        setUpTabBar()

    }


    private fun setUpTabBar() {
        val adapter = FragmentPagerItemAdapter(
            childFragmentManager,
            FragmentPagerItems.with(activity)
                .add("Live", MonitoringFragment::class.java)
                .add("Data", GraphFragment::class.java)
                .add("Control", TableFragment::class.java)
                .create()
        )

        mBinding.viewpager.adapter = adapter
        mBinding.viewpagertab.setViewPager(mBinding.viewpager)
    }


}