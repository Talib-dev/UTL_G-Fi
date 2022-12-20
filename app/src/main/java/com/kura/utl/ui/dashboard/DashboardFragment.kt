package com.kura.utl.ui.dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kura.utl.R
import com.kura.utl.databinding.FragmentDeshboredBinding
import com.kura.utl.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDeshboredBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_deshbored
    private lateinit var mBinding: FragmentDeshboredBinding
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: DashboardAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentUser?.let { user ->
            mBinding = getDataBinding()
            adapter = DashboardAdapter(viewModel.systemList) {
                navigateToProduct(it.serialNo)
            }
            getDataBinding().toolbar.searchInput.addTextChangedListener(object :
                TextWatcher {


                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable) {
                    if (!isAdded)
                        return
                    adapter.filter.filter(s.toString().trim())
                }
            })
            getDataBinding().rvDeviceList.adapter = adapter
            initClickListener()
            viewModel.getUserInfo(user.uid)
            viewModel.getSystem(user.uid)

            viewModel.systemInfo.observe(viewLifecycleOwner) {
                adapter.update(it)
            }
            viewModel.userInfo.observe(viewLifecycleOwner) {
                if (it.userType > 0) {
                    mBinding.llUsers.visibility = View.VISIBLE
                    mBinding.llTotalLocation.visibility = View.VISIBLE
                    mBinding.llTotalSystem.visibility = View.VISIBLE
                    mBinding.llTotalFaults.visibility = View.VISIBLE
                    initObserver()
                } else {
                    mBinding.llUsers.visibility = View.GONE
                    mBinding.llTotalLocation.visibility = View.GONE
                    mBinding.llTotalSystem.visibility = View.GONE
                    mBinding.llTotalFaults.visibility = View.GONE
                }
            }
        } ?: run {
            navigateToLogin()
        }
    }

    private fun initObserver() {
        viewModel.userCount.observe(viewLifecycleOwner) {
            mBinding.tvTUser.text = it.toString()
        }
        viewModel.systemCount.observe(viewLifecycleOwner) {
            mBinding.tvSystemCount.text = it.toString()
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

    private fun navigateToProduct(sNo:String) {
        val action = DashboardFragmentDirections.actionDashboardFragmentToProductDetailsFragment(sNo)
        findNavController().navigate(action)
    }
}
