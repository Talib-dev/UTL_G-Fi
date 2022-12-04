package com.kura.utl.datalayer.repository

import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

interface DatabaseRepository {
    suspend fun getProductDetails(serialNo:String)
}