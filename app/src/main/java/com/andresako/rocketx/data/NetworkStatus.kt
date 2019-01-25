package com.andresako.rocketx.data

import androidx.annotation.StringRes

data class NetworkStatus constructor(
    val status: Status,
    @StringRes
    val msg: Int? = null
) {

    companion object {
        val SUCCESS = NetworkStatus(Status.SUCCESS)
        val RUNNING = NetworkStatus(Status.RUNNING)

        fun error(@StringRes msg: Int) = NetworkStatus(Status.FAILED, msg)
    }
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}