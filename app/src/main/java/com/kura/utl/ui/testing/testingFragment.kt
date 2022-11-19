package com.kura.utl.ui.testing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kura.utl.R
import com.kura.utl.Utils.Utils
import com.kura.utl.databinding.FragmentDeviceConfigurationBinding
import com.kura.utl.databinding.FragmentTetingBinding
import com.kura.utl.datalayer.modal.Device
import com.kura.utl.ui.base.BaseFragment


class TestingFragment : BaseFragment<FragmentTetingBinding>() {
    private lateinit var mBinding: FragmentTetingBinding

    override fun getLayoutId(): Int = R.layout.fragment_teting
    private var fAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getDataBinding()
        mBinding.btAdd.setOnClickListener {
            fAuth.uid?.let {
                addInDB(Device(serialNo = mBinding.editText.text.toString().trim()),it)
            }
        }


    }

    private fun addInDB(device: Device,uid:String) {
            database.getReference(device.serialNo.substring(0, 3)).child(Utils.deviceInfo)
                .child(device.serialNo).setValue(device)
                .addOnSuccessListener {
                   toast("Successful")
                }
                .addOnFailureListener {
                    toast(it.message.toString())
                }
        }


    

}