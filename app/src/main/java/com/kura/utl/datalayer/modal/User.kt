package com.kura.utl.datalayer.modal

/** userType 0 - user
 * 1 - admin
 * 2 - super admin
 */
data class User(
    val fullName: String,
    val email: String,
    val password: String,
    val phone: String,
    val deviceID: String,
    val userType: Int
)
