package com.kura.utl.ui.productDetails.control

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.kura.utl.Utils.Utils
import com.kura.utl.datalayer.modal.DeviceDataModel
import com.kura.utl.datalayer.modal.Setting
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ControlViewModel @Inject constructor(
) : ViewModel() {

    private var dbRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("001100003".substring(0, 3))

    private val dataMutable = MutableLiveData<Setting>()
    val data: LiveData<Setting> = dataMutable


    fun getData(serialNo: String) {
        dbRef.child(Utils.deviceControl).child("11003")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        snapshot.getValue(Setting::class.java)?.let {
                            dataMutable.postValue(it)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MonitoringViewModel", "onCancelled: ${error.message}")
                }
            })

    }

    fun setData(checked: Boolean) {
        dbRef.child(Utils.deviceControl).child("11003").setValue(Setting("On/Off ",checked))
    }

}