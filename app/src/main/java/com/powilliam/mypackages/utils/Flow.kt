package com.powilliam.mypackages.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> withInterval(delayInMilliseconds: Long, handler: suspend () -> T): Flow<T> = flow {
    while (true) {
        val value = handler()
        emit(value)
        delay(delayInMilliseconds)
    }
}