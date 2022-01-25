package com.powilliam.mypackages.data.entity

sealed class EventType(val type: String) {
    object BDE : EventType("BDE")
    object BDI : EventType("BDI")
    object BDR : EventType("BDR")
    object BLQ : EventType("BLQ")
    object PAR : EventType("PAR")
    object EST : EventType("EST")
    object RO : EventType("RO")
    object DO : EventType("DO")
    object FC : EventType("FC")
    object LDE : EventType("LDE")
    object LDI : EventType("LDI")
    object OEC : EventType("OEC")
    object PO : EventType("PO")
}

data class Event(val type: EventType, val description: String, val location: Location)
