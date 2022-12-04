package com.kura.utl.ui.productDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.kura.utl.R
import com.kura.utl.databinding.FragmentLoginBinding
import com.kura.utl.databinding.FragmentProductDetailsBinding
import com.kura.utl.ui.base.BaseFragment


class ProductDetailsFragment : BaseFragment<FragmentProductDetailsBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_product_details
    private lateinit var mBinding: FragmentProductDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()

    }

}