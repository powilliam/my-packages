package com.powilliam.mypackages.data.entity

data class Location(val address: Address, val type: String) {
    fun toMap(): Map<String, Any?> = mapOf("address" to address.toMap(), "type" to type)

    companion object {
        fun fromMap(map: Map<String, Any?>): Location = Location(
            address = Address.fromMap(map["address"] as Map<String, Any?>),
            type = map["type"] as String
        )
    }
}


