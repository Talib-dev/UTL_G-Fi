package com.kura.utl.ui.productDetails.monitoring

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.kura.utl.Utils.Utils
import com.kura.utl.datalayer.modal.DeviceDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MonitoringViewModel @Inject constructor(
) : ViewModel() {


    private var dbRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("001100003".substring(0, 3))

    private val systemInfoMutable = MutableLiveData<DeviceDataModel>()
    val systemInfo: LiveData<DeviceDataModel> = systemInfoMutable


    fun getSystem(serialNo: String) {
        dbRef.child(Utils.deviceData).child("11003").startA.orderByKey().limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (postSnapshot in snapshot.children) {
                            Log.d("MonitoringViewModel", postSnapshot.value.toString())
                            postSnapshot.getValue(DeviceDataModel::class.java)?.let {
                                systemInfoMutable.postValue(it)

                            }
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MonitoringViewModel", "onCancelled: ${error.message}")
                }
            })

    }


}