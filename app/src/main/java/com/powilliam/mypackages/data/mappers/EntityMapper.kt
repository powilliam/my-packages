package com.powilliam.mypackages.data.mappers

interface EntityMapper<Entity> {
    fun toMap(entity: Entity): Map<String, Any>
    fun fromMap(map: Map<String, Any>): Entity
}