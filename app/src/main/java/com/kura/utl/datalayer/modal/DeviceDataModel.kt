package com.kura.utl.datalayer.modal

data class DeviceDataModel(
    val Battery: Battery,
    val Breaker: Breaker,
    val Bypass: Bypass,
    val Comm: String,
    val Display: String,
    val InputAC: InputAC,
    val Mode: Mode,
    val OutputAC: OutputAC,
    val OutputDC: OutputDC,
    val Solar: Solar,
    val Status: Status,
    val SysInfo: SysInfo,
    val Temp: Temp
)