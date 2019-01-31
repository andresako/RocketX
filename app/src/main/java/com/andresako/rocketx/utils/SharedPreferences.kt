package com.andresako.rocketx.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreferences(private val context: Context) : SharedPreferencesInterface {

    private val ROCKETX_SP = "ROCKETX_SP"
    private val FIRST_LOAD = "FIRST_LOAD"

    override fun setFirstLoad(isFirstLoad: Boolean) {
        context.getSharedPreferences(ROCKETX_SP, MODE_PRIVATE)
            .edit()
            .putBoolean(FIRST_LOAD, isFirstLoad)
            .apply()
    }

    override fun getFirstLoad(): Boolean =
        context.getSharedPreferences(ROCKETX_SP, MODE_PRIVATE)
            .getBoolean(FIRST_LOAD, true)
}


interface SharedPreferencesInterface {

    fun setFirstLoad(isFirstTime: Boolean)

    fun getFirstLoad(): Boolean
}