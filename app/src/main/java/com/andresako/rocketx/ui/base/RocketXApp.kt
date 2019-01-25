package com.andresako.rocketx.ui.base

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class RocketXApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerRocketXAppComponent.builder().create(this)
    }
}