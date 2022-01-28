package com.powilliam.mypackages.data.entity

data class Coordinates(val latitude: Double, val longitude: Double) {
    fun toMap(): Map<String, Any?> = mapOf("latitude" to latitude, "longitude" to longitude)

    companion object {
        fun fromMap(map: Map<String, Any?>): Coordinates = Coordinates(
            latitude = map["latitude"] as Double,
            longitude = map["longitude"] as Double
        )
    }
}
