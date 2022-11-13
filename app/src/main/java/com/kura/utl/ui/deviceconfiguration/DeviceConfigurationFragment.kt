package com.kura.utl.ui.deviceconfiguration

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kura.utl.R
import com.kura.utl.Utils.Utils
import com.kura.utl.databinding.FragmentDeviceConfigurationBinding
import com.kura.utl.ui.base.BaseFragment
import com.kura.utl.ui.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket


@AndroidEntryPoint
class DeviceConfigurationFragment : BaseFragment<FragmentDeviceConfigurationBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_device_configuration
    private lateinit var mBinding: FragmentDeviceConfigurationBinding
    private val scope = CoroutineScope(Dispatchers.Main)


    lateinit var tcpClient: TCPClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tcpClient = TCPClient()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        mBinding.btGotoWifi.setOnClickListener {
            gotoWiFi()
        }
        mBinding.btSendMsg.setOnClickListener {
            connectWithDevice()
        }
    }

    private fun gotoWiFi() {
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }

    private fun connectWithDevice() {
        tcpClient.connectInSeparateThread(Utils.deviceIP, Utils.devicePORT)
        lifecycleScope.launch {
            tcpClient.setListener(object : TCPClient.OnConnectionListener {
                override fun connected(socket: Socket, ip: String, port: Int) {
                    Log.e("DeviceConfigurationFragment", "connected " )
                }

                override fun messageReceived(message: String) {
                    when (message) {
                        "OK" -> {
                            Log.e("DeviceConfigurationFragment", "successfully registered " )
                            navigateToDashboard()

                        }
                        "FAIL" -> {
                            Toast.makeText(context, "registered failed", Toast.LENGTH_SHORT).show()
                            Log.e("DeviceConfigurationFragment", "registered failed" )
                        }
                        else -> {
                            Log.e("DeviceConfigurationFragment", message )

                        }
                    }
                }

                override fun disconnected(ip: String, port: Int) {
                    Log.e("DeviceConfigurationFragment", "disconnected: ", )
                }
            })

        }
    }


    private fun navigateToDashboard() {
        val action = DeviceConfigurationFragmentDirections.actionDeviceConfigurationFragmentToDashboardFragment()
        findNavController().navigate(action)
    }
}