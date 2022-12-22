package com.kura.utl.datalayer.modal

data class InputAC(
    val ActPower: ActPower?=null,
    val ApparPower: ApparPower?=null,
    val Curr: Curr?=null,
    val Frequency: Frequency?=null,
    val LoadPerc: LoadPerc?=null,
    val PF: PF?=null,
    val ReactPower: ReactPower?=null,
    val Volt: Volt?=null,
)