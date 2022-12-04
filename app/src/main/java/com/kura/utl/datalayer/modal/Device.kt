package com.kura.utl.datalayer.modal

import androidx.annotation.Keep

@Keep
data class Device(
    val uid: String="",
    val serialNo: String="",
    val sysName: String="",
    val model: String="",
    val location: String="",
    val activationStatus: String="",
    val installationDate: String="",
    val lastUpdate: String="",
    val warranty: Boolean=true,
    val email: String="",
    val lastPing: String="",
    )