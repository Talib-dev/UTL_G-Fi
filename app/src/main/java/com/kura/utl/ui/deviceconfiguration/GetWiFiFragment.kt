package com.kura.utl.ui.deviceconfiguration

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kura.utl.R
import com.kura.utl.databinding.FragmentGetWifiBinding
import com.kura.utl.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GetWiFiFragment : BaseFragment<FragmentGetWifiBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_get_wifi
    private lateinit var mBinding: FragmentGetWifiBinding
    private val args: GetWiFiFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        mBinding.btNext.setOnClickListener {
            val name = mBinding.getwifiacwifiname.text.toString().trim()
            val password = mBinding.getwifiPassword.text.toString().trim()
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                if (TextUtils.isEmpty(name)) {
                    toast("Email is Required")

                }
                if (TextUtils.isEmpty(password)) {
                    toast("Password is Required.")

                }
            } else {
                navigateToConfiguration(
                    args.deviceId, name, password
                )
            }
        }
    }

    private fun navigateToConfiguration(deviceId: String, name: String, password: String) {
        val action = GetWiFiFragmentDirections.actionGetWiFiFragmentToDeviceConfigurationFragment(
            deviceId,
            name,
            password
        )
        findNavController().navigate(action)
    }
}