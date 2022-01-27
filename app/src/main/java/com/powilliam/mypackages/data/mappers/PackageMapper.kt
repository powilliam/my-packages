package com.powilliam.mypackages.data.mappers

import com.powilliam.mypackages.data.entity.Package
import javax.inject.Inject

class PackageMapper @Inject constructor() : EntityMapper<Package> {
    override fun toMap(entity: Package): Map<String, Any> = mapOf(
        "name" to entity.name,
        "tracker" to entity.tracker,
    )

    override fun fromMap(map: Map<String, Any>): Package =
        Package(name = map["name"] as String, tracker = map["tracker"] as String)
}