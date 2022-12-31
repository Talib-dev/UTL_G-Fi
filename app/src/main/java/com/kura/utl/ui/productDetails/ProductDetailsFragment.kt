package com.kura.utl.ui.productDetails

import android.os.Bundle
import android.view.View
import com.kura.utl.R
import com.kura.utl.databinding.FragmentProductDetailsBinding
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.productDetails.control.ControlFragment
import com.kura.utl.ui.productDetails.data.DataFragment
import com.kura.utl.ui.productDetails.monitoring.MonitoringFragment
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems


class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_product_details
    private lateinit var mBinding: FragmentProductDetailsBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        mBinding.toolbar.toolbar.title="Monitoring"
        setUpTabBar()

    }
    private fun setUpTabBar() {
        val adapter = FragmentPagerItemAdapter(
            childFragmentManager,
            FragmentPagerItems.with(activity)
                .add("Live", MonitoringFragment::class.java)
                .add("Data", DataFragment::class.java)
                .add("Control", ControlFragment::class.java)
                .create()
        )

        mBinding.viewpager.adapter = adapter
        mBinding.viewpagertab.setViewPager(mBinding.viewpager)
    }


}