package com.acorpas.rickmortychallenge.extension

import timber.log.Timber

fun Any.logd(message: String) {
    Timber.d(message)
}

fun Any.logw(message: String) {
    Timber.w( message)
}

fun Any.loge(message: String) {
    Timber.e( message)
}

fun Any.loge(message: String,e: Throwable) {
    Timber.e(message,e)
}

fun Any.logv(message: String) {
    Timber.v( message)
}

fun Any.logi(message: String) {
    Timber.i( message)
}

fun Any.logwtf(message: String) {
    Timber.wtf( message)
}