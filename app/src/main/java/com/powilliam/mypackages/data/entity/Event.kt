package com.powilliam.mypackages.data.entity

data class Event(val type: String, val description: String, val location: Location) {
    fun toMap(): Map<String, Any?> =
        mapOf("type" to type, "description" to description, "location" to location.toMap())

    companion object {
        fun fromMap(map: Map<String, Any?>): Event = Event(
            type = map["type"] as String,
            description = map["description"] as String,
            location = Location.fromMap(map["location"] as Map<String, Any?>),
        )
    }
}
