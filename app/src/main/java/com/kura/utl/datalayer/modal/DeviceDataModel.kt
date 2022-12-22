package com.kura.utl.datalayer.modal

data class DeviceDataModel(
    val Battery: Battery?=null,
    val Breaker: Breaker?=null,
    val Bypass: Bypass?=null,
    val Comm: String?=null,
    val Display: String?=null,
    val InputAC: InputAC?=null,
    val Mode: Mode?=null,
    val OutputAC: OutputAC?=null,
    val OutputDC: OutputDC?=null,
    val Solar: Solar?=null,
    val Status: Status?=null,
    val SysInfo: SysInfo?=null,
    val Temp: Temp?=null,
)