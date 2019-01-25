package com.andresako.rocketx.ui.base

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        RocketXAppModule::class,
        ActivityBuilderModule::class
    ]
)

interface RocketXAppModule : AndroidInjector<RocketXApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<RocketXApp>()
}