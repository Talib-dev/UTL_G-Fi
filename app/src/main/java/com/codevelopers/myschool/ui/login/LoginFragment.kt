package com.codevelopers.myschool.ui.login

import android.os.Bundle
import android.view.View
import com.codevelopers.myschool.R
import com.codevelopers.myschool.databinding.FragmentLoginBinding
import com.codevelopers.myschool.ui.base.BaseFragment
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
