package com.andresako.rocketx.ui.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.andresako.rocketx.data.RocketApiService
import com.andresako.rocketx.data.database.RocketXDatabase
import com.andresako.rocketx.data.repository.LaunchRepo
import com.andresako.rocketx.utils.FragmentScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(
    modules = [
        LaunchDetailsFragmentModule::class
    ]
)
interface LaunchesDetailsFragmentSubComponent : AndroidInjector<LaunchDetailsFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<LaunchDetailsFragment>()
}

@Module
class LaunchDetailsFragmentModule {

    @Provides
    fun getLaunchRepo(
        rocketApiService: RocketApiService,
        rocketXDatabase: RocketXDatabase
    ): LaunchRepo {
        return LaunchRepo(rocketApiService, rocketXDatabase.launchDAO())
    }

    @Provides
    fun getLaunchDetailsViewModel(
        launchRepo: LaunchRepo,
        target: LaunchDetailsFragment
    ): LaunchDetailsViewModel =
        ViewModelProviders
            .of(target, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return LaunchDetailsViewModel(launchRepo) as T
                }
            })[LaunchDetailsViewModel::class.java]
}