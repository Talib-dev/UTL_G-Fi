package com.kura.utl.datalayer.modal

data class Bypass(
    val ActPower: ActPower,
    val ApparPower: ApparPower,
    val Curr: Curr,
    val Frequency: Frequency,
    val LoadPerc: LoadPerc,
    val PF: PF,
    val ReactPower: ReactPower,
    val Volt: Volt
)