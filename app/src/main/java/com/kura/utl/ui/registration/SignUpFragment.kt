package com.kura.utl.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kura.utl.R
import com.kura.utl.databinding.FragmentLoginBinding
import com.kura.utl.databinding.FragmentSignUpBinding
import com.kura.utl.ui.base.BaseFragment


class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_sign_up
    private lateinit var mBinding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()


    }
}