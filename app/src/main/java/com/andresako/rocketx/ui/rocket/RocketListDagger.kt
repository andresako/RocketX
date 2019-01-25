package com.andresako.rocketx.ui.rocket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.andresako.rocketx.data.RocketApiService
import com.andresako.rocketx.data.database.RocketXDatabase
import com.andresako.rocketx.data.repository.RocketRepo
import com.andresako.rocketx.utils.FragmentScope
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.android.AndroidInjector

@FragmentScope
@Subcomponent(
    modules = [
        RocketListModule::class
    ]
)
interface RocketListSubComponent : AndroidInjector<RocketListFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RocketListFragment>()
}

@Module
class RocketListModule {

    @Provides
    fun getRocketRepo(
        rocketXAPI: RocketApiService,
        rocketXDB: RocketXDatabase
    ): RocketRepo {
        return RocketRepo(rocketXAPI, rocketXDB.rocketDAO())
    }

    @Provides
    fun getRocketListViewModel(
        rocketRepo: RocketRepo,
        target: RocketListFragment
    ): RocketListViewModel =
        ViewModelProviders.of(target, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RocketListViewModel(rocketRepo) as T
            }
        }).get(RocketListViewModel::class.java)
}