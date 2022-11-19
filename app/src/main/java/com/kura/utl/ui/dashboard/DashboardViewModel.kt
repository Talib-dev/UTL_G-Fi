package com.kura.utl.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kura.utl.Utils.Utils
import com.kura.utl.datalayer.modal.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
) : ViewModel() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    private val TAG: String = "DashboardViewModel"
    var systemList=ArrayList<Device>()


    private var dbRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("001100003".substring(0, 3))

    init {
        getUsersCount()
        getSystemCount()
    }

    private val userCountMutable = MutableLiveData<Long>()
    val userCount: LiveData<Long> = userCountMutable

    private val systemCountMutable = MutableLiveData<Long>()
    val systemCount: LiveData<Long> = systemCountMutable

    private val systemInfoMutable = MutableLiveData<List<Device>>()
    val systemInfo: LiveData<List<Device>> = systemInfoMutable


    private fun getUsersCount() {
        dbRef.child(Utils.userInfo)
            .addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    userCountMutable.postValue(snapshot.childrenCount)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: ${error.message}")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }
            })

    }

    private fun getSystemCount() {
        dbRef.child(Utils.deviceInfo)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    systemCountMutable.postValue(snapshot.childrenCount)
                    (Dispatchers.IO)
                    for (postSnapshot in snapshot.children) {
                        postSnapshot.getValue(Device::class.java)?.let {
                            systemList.add(it)
                        }


                    }
                    systemInfoMutable.postValue(systemList)

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: ${error.message}")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }
            })
    }


}