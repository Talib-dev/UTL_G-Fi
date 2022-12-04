package com.kura.utl.datalayer.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.kura.utl.Utils.Utils
import com.kura.utl.datalayer.modal.User
import javax.inject.Inject

class DatabaseRepositoryImp @Inject constructor(private val databaseReference: DatabaseReference) :
    DatabaseRepository {
    override suspend fun  getProductDetails(serialNo: String) {
       val result= databaseReference.child("001100").child(Utils.deviceData).child(serialNo).get().await()

    }
}