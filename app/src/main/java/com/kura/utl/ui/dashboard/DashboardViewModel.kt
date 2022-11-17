package com.kura.utl.ui.dashboard

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.*
import com.kura.utl.Utils.Utils
import com.kura.utl.datalayer.modal.User
import com.kura.utl.datalayer.network.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class DashboardViewModel @Inject constructor(
) : ViewModel() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    private val TAG: String = "DashboardViewModel"


    var dbRef: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("001100003".substring(0, 3))

    init {
        getUsersCount()
        getSystemCount()
    }

    private val userCountMutable = MutableLiveData<Long>()
    val userCount: LiveData<Long> = userCountMutable

    private val systemCountMutable = MutableLiveData<Long>()
    val systemCount: LiveData<Long> = systemCountMutable


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