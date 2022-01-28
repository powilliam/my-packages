package com.powilliam.mypackages.data.entity

data class Address(val city: String, val province: String, val coordinates: Coordinates?) {
    fun toMap(): Map<String, Any?> =
        mapOf("city" to city, "province" to province, "coordinates" to coordinates?.toMap())

    companion object {
        fun fromMap(map: Map<String, Any?>): Address = Address(
            city = map["city"] as String,
            province = map["province"] as String,
            coordinates = if (map["coordinates"] != null) Coordinates.fromMap(map["coordinates"] as Map<String, Any?>) else null
        )
    }
}
