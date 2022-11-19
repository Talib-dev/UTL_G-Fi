package com.kura.utl.datalayer.modal

data class Device(
    val uId: String="",
    val serialNo: String="#123CDDL",
    val sysName: String="jPCU",
    val model: String="ALPHA+",
    val location: String="LUCKNOW",
    val activationStatus: String="12/Jan/2021",
    val installationDate: String="1/Jun/2025",
    val lastUpdate: String="1/Jun2021",
    val warranty: Boolean=true,
    val email: String="ABC@GMAIL.COM",
    val lastPing: String="1-Jan-22 0:00:10",
    )