package com.andresako.rocketx.ui

import com.andresako.rocketx.ui.rocket.RocketListSubComponent
import com.andresako.rocketx.utils.ActivityScope
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(
    modules = [
        MainActivityModule::class,
        FragmentBuilderModule::class
    ]
)
interface MainActivitySubComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(
    subcomponents = [
        RocketListSubComponent::class
    ]
)
class MainActivityModule