package com.powilliam.mypackages.utils

fun <T> List<T>.jsonMap() = foldIndexed(mutableMapOf<String, Any?>()) { position, map, item ->
    map["$position"] = item
    map
}