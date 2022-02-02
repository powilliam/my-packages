package com.powilliam.mypackages.data.entity

import com.powilliam.mypackages.utils.jsonMap

data class Package(
    val name: String,
    val tracker: String,
    val events: List<Event> = emptyList()
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "name" to name,
        "tracker" to tracker,
        "events" to events.jsonMap()
    )

    companion object {
        fun fromMap(map: Map<String, Any?>): Package = Package(
            name = map["name"] as String,
            tracker = map["tracker"] as String,
            events = if (map["events"] != null) (map["events"] as List<Map<String, Any?>>)
                .map { Event.fromMap(it) } else emptyList()
        )
    }
}
