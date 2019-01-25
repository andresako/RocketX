package com.andresako.rocketx.ui

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ActivityBuilderModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    abstract fun bindMainActivityInjectorFactory(
        builder: MainActivitySubComponent.Builder
    ): AndroidInjector.Factory<*>
}