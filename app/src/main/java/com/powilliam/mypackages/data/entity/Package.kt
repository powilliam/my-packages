package com.powilliam.mypackages.data.entity

data class Package(
    val name: String,
    val tracker: String,
    val events: List<Event> = emptyList()
)