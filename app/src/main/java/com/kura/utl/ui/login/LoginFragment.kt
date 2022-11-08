package com.kura.utl.ui.login

import android.os.Bundle
import android.view.View
import com.kura.utl.R
import com.kura.utl.databinding.FragmentLoginBinding
import com.kura.utl.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_login
    private lateinit var mBinding:FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding=getDataBinding()


    }
}
