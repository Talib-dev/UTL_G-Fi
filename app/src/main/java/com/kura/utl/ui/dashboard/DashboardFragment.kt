package com.kura.utl.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kura.utl.R
import com.kura.utl.Utils.Utils
import com.kura.utl.databinding.FragmentDeshboredBinding
import com.kura.utl.datalayer.modal.User
import com.kura.utl.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDeshboredBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_deshbored
    private lateinit var mBinding: FragmentDeshboredBinding
    private val viewModel: DashboardViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentUser?.let {
            mBinding = getDataBinding()
            initClickListener()
            initObserver()


        } ?: run {
            navigateToLogin()
        }
    }

    private fun initObserver() {
        viewModel.userCount.observe(viewLifecycleOwner){
            mBinding.tvTUser.text=it.toString()
        }
        viewModel.systemCount.observe(viewLifecycleOwner){
            mBinding.tvSystemCount.text=it.toString()
        }
    }

    private fun initClickListener() {
        mBinding.toolbar.toolbar.inflateMenu(R.menu.home_menu)
        mBinding.toolbar.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
        mBinding.efAddDevice.setOnClickListener {
            navigateToGetWiFiFragment()
        }

        mBinding.toolbar.ivBack.setOnClickListener {
            mBinding.toolbar.toolbar.visibility = View.VISIBLE
            mBinding.toolbar.clSearch.visibility = View.GONE

        }
        mBinding.toolbar.ivCancel.setOnClickListener {
            mBinding.toolbar.clTags.visibility = View.VISIBLE
            mBinding.toolbar.ivCancel.visibility = View.GONE
            mBinding.toolbar.tvTag.visibility = View.GONE
        }
        mBinding.toolbar.tvName.setOnClickListener {
            setTags(mBinding.toolbar.tvName.text.toString())
        }
        mBinding.toolbar.tvModel.setOnClickListener {
            setTags(mBinding.toolbar.tvModel.text.toString())
        }
        mBinding.toolbar.tvLocation.setOnClickListener {
            setTags(mBinding.toolbar.tvLocation.text.toString())
        }
        mBinding.toolbar.tvBattery.setOnClickListener {
            setTags(mBinding.toolbar.tvBattery.text.toString())
        }
        mBinding.toolbar.tvInputType.setOnClickListener {
            setTags(mBinding.toolbar.tvInputType.text.toString())
        }
        mBinding.toolbar.tvOutputType.setOnClickListener {
            setTags(mBinding.toolbar.tvOutputType.text.toString())
        }


    }


    private fun setTags(tag: String) {
        mBinding.toolbar.ivCancel.visibility = View.VISIBLE
        mBinding.toolbar.tvTag.visibility = View.VISIBLE
        mBinding.toolbar.clTags.visibility = View.GONE
        mBinding.toolbar.tvTag.text = tag

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                mBinding.toolbar.toolbar.visibility = View.GONE
                mBinding.toolbar.clSearch.visibility = View.VISIBLE
                true
            }
            R.id.menu_logout -> {
                FirebaseAuth.getInstance()
                    .signOut()
                navigateToLogin()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun navigateToGetWiFiFragment() {
        val action =
            DashboardFragmentDirections.actionDashboardFragmentToGetWiFiFragment("jbfcsabjvsuygcbsv")
        findNavController().navigate(action)
    }

    private fun navigateToLogin() {
        val action = DashboardFragmentDirections.actionDashboardFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}
