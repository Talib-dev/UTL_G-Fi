package com.kura.utl.ui.dashboard

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kura.utl.R
import com.kura.utl.databinding.FragmentDeshboredBinding
import com.kura.utl.ui.base.BaseFragment
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
            mBinding.toolbar.toolbar.inflateMenu(R.menu.home_menu)
            mBinding.toolbar.toolbar.setOnMenuItemClickListener {
                onOptionsItemSelected(it)
            }
           mBinding.efAddDevice.setOnClickListener {
               navigateToGetWiFiFragment()
           }
       }?:run{
          navigateToLogin()
       }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_configuration -> {
                // TODO: User clicked the save button
                true
            }
            else -> super.onOptionsItemSelected(item)
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
