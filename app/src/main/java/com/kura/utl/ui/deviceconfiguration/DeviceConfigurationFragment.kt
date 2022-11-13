package com.kura.utl.ui.deviceconfiguration

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kura.utl.R
import com.kura.utl.Utils.Utils
import com.kura.utl.databinding.FragmentDeviceConfigurationBinding
import com.kura.utl.datalayer.modal.Device
import com.kura.utl.datalayer.modal.User
import com.kura.utl.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.Socket


@AndroidEntryPoint
class DeviceConfigurationFragment : BaseFragment<FragmentDeviceConfigurationBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_device_configuration
    private lateinit var mBinding: FragmentDeviceConfigurationBinding
    lateinit var tcpClient: TCPClient
    private val handler = Handler()
    private lateinit var runnable: Runnable
    private val delay: Long = 1000
    private var mStatus = 0
    private var dataString = ""
    private val args: DeviceConfigurationFragmentArgs by navArgs()
    private  var fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("UTL_G-Fi")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tcpClient = TCPClient()
        dataString = args.deviceId + "," + args.wifiName + ","+args.wifiPassword
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        mBinding.btGotoWifi.setOnClickListener {
            gotoWiFi()
        }
        mBinding.btSendMsg.setOnClickListener {
            connectWithDevice()
            setStatus(1)
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
                    Log.e("DeviceConfigurationFragment", "connected ")
                    setStatus(2)
                    sendDataString()
                }

                override fun messageReceived(message: String) {
                    when (message) {
                        "OK" -> {
                            Log.e("DeviceConfigurationFragment", "successfully registered ")
                            setStatus(4)
                            addInDB()

                        }
                        "FAIL" -> {
                            Toast.makeText(context, "registered failed", Toast.LENGTH_SHORT).show()
                            Log.e("DeviceConfigurationFragment", "registered failed")
                            setStatus(-1)
                        }
                        else -> {
                            Log.e("DeviceConfigurationFragment", message)
                        }
                    }
                }

                override fun disconnected(ip: String, port: Int) {
                    Log.e("DeviceConfigurationFragment", "disconnected: ")
                    setStatus(0)
                }
            })

        }
    }

    fun setStatus(status: Int) {
        lifecycleScope.launch{

            mStatus = status
            when (status) {
                -1 -> {
                    mBinding.tvStatus.text = getText(R.string.failed)
                    mBinding.progressBar.visibility = View.GONE

                }
                0 -> {
                    mBinding.tvStatus.text = getText(R.string.disconnecting)
                    mBinding.progressBar.visibility = View.GONE
                }

                1 -> {
                    mBinding.tvStatus.text = getText(R.string.connecting)
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                2 -> {
                    mBinding.tvStatus.text = getText(R.string.connected)
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                3 -> {
                    mBinding.tvStatus.text = getText(R.string.sending_data)
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                4 -> {
                    mBinding.tvStatus.text = getText(R.string.connecting_device_to_server)
                    mBinding.progressBar.visibility = View.VISIBLE
                }
                5 -> {
                    mBinding.tvStatus.text = getText(R.string.added_device)
                    mBinding.progressBar.visibility = View.GONE
                }


            }
        }

    }

    fun sendDataString() {
        setStatus(3)
        handler.postDelayed(Runnable {
            sendData(dataString)
            if (tcpClient.isConnected() && mStatus > 3) {
                handler.postDelayed(runnable, delay)
            }
        }.also { runnable = it }, delay)
    }

    private fun sendData(msg: String) {
        Thread {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            tcpClient.send(msg)
            Log.d(
                "",
                "sendData: $msg"
            )
        }.start()
    }


    private fun addInDB() {
        fAuth.uid?.let { user ->
            val device=Device(args.deviceId, user)
            myRef.child(user).child(Utils.userInfo).setValue(device)
                .addOnSuccessListener {
                    setStatus(5)
                    navigateToDashboard()
                }
                .addOnFailureListener {
                    toast(it.message.toString())
                }
        }?:run {
            toast(getString(R.string.error))
        }


    }

    private fun navigateToDashboard() {
        val action =
            DeviceConfigurationFragmentDirections.actionDeviceConfigurationFragmentToDashboardFragment()
        findNavController().navigate(action)
    }
}