package com.andresako.rocketx.data

import androidx.annotation.StringRes

data class NetworkStatus constructor(
    val status: Status,
    @StringRes
    val msg: Int? = null
) {

    companion object {
        val DONE = NetworkStatus(Status.DONE)
        val RUNNING = NetworkStatus(Status.RUNNING)

        fun error(@StringRes msg: Int) = NetworkStatus(Status.FAILED, msg)
    }
}

enum class Status {
    RUNNING,
    DONE,
    FAILED
}