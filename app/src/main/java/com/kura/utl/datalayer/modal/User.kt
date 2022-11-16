package com.kura.utl.datalayer.modal

import java.util.*
import kotlin.collections.ArrayList

/** userType 0 - user
 * 1 - admin
 * 2 - super admin
 */
data class User(
    val userName: String,
    val email: String,
    val password: String,
    val phone: String,
    val deviceID: ArrayList<String>,
    val userType: Int,
    var uid: String=""
)
