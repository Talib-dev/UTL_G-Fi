package com.kura.utl.ui.productDetails.data

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
class DataViewModel @Inject constructor(
) : ViewModel() {

    var startTime: String = ""
    var endTime: String = ""
    var dataList = arrayListOf<DeviceDataModel>()
    private var dbRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("001100003".substring(0, 3))

    private val dataMutable = MutableLiveData<List<DeviceDataModel>>()
    val data: LiveData<List<DeviceDataModel>> = dataMutable


    fun getData(serialNo: String) {
        dbRef.child(Utils.deviceData).child("11003")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (postSnapshot in snapshot.children) {
                            Log.d("MonitoringViewModel", postSnapshot.value.toString())
                            postSnapshot.getValue(DeviceDataModel::class.java)?.let {
                                dataList.add(it)
                            }
                        }
                        dataMutable.postValue(dataList)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MonitoringViewModel", "onCancelled: ${error.message}")
                }
            })

    }

}