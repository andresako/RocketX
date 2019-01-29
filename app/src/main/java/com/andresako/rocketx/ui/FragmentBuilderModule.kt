package com.andresako.rocketx.ui

import com.andresako.rocketx.ui.launch.LaunchDetailsFragment
import com.andresako.rocketx.ui.launch.LaunchesDetailsFragmentSubComponent
import com.andresako.rocketx.ui.rocket.RocketListFragment
import com.andresako.rocketx.ui.rocket.RocketListSubComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class FragmentBuilderModule {

    @Binds
    @IntoMap
    @ClassKey(RocketListFragment::class)
    internal abstract fun bindHomeFragmentInjectorFactory(
        builder: RocketListSubComponent.Builder
    ): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(LaunchDetailsFragment::class)
    internal abstract fun bindLaunchesFragmentInjectorFactory(
        builder: LaunchesDetailsFragmentSubComponent.Builder
    ): AndroidInjector.Factory<*>
}