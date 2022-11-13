package com.kura.utl.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kura.utl.R
import com.kura.utl.databinding.FragmentDeshboredBinding
import com.kura.utl.databinding.FragmentLoginBinding
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment: BaseFragment<FragmentDeshboredBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_deshbored
    private lateinit var mBinding: FragmentDeshboredBinding
    private  var fAuth: FirebaseAuth= FirebaseAuth.getInstance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       fAuth.currentUser?.let {
           mBinding=getDataBinding()
           mBinding.efAddDevice.setOnClickListener {
               navigateToGetWiFiFragment()
           }
       }?:run{
          navigateToLogin()
       }


    }


    private fun navigateToGetWiFiFragment() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToGetWiFiFragment("jbfcsabjvsuygcbsv")
        findNavController().navigate(action)
    }
    private fun navigateToLogin() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}
