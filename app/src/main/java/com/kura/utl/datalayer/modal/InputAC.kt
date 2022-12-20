package com.kura.utl.datalayer.modal

data class InputAC(
    val ActPower: ActPower,
    val ApparPower: ApparPowerX,
    val Curr: CurrX,
    val Frequency: FrequencyX,
    val LoadPerc: LoadPercX,
    val PF: PFX,
    val ReactPower: ReactPowerX,
    val Volt: VoltX
)