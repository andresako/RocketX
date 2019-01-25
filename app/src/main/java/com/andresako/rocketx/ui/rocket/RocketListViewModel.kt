package com.andresako.rocketx.ui.rocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andresako.rocketx.data.NetworkStatus
import com.andresako.rocketx.data.repository.RocketRepoInterface
import com.andresako.rocketx.data.room.entity.RocketEntity

interface RocketListViewModelInterface {
    fun isFirstTime(): Boolean

    fun getRockets(): LiveData<List<RocketEntity>>

    fun loadRockets(refresh: Boolean)

    fun getActiveRockets(filterOn: Boolean)

    fun getNetworkStatus(): LiveData<NetworkStatus>

}

class RocketListViewModel(
    private val rocketRepo: RocketRepoInterface
) : ViewModel(), RocketListViewModelInterface {

    private val rocketsMutableLive = MutableLiveData<List<RocketEntity>>()
    private val networkStatus = rocketRepo.getNetworkStatusData()

    private var filterOn = false
    private var rockets = listOf<RocketEntity>()


    override fun isFirstTime(): Boolean {
//        if (it) prefsHelper.storeIsFirstTime(false)
        return false
    }

    override fun getRockets(): LiveData<List<RocketEntity>> = rocketsMutableLive


    override fun loadRockets(refresh: Boolean) {
        if (rockets.isNotEmpty() && !refresh) {
            updateLiveData(rockets, filterOn)
        } else {
            getDataFromRepo(refresh)
        }
    }

    override fun getActiveRockets(filterOn: Boolean) {
    }

    override fun getNetworkStatus() = this.networkStatus

    private fun updateLiveData(rockets: List<RocketEntity>, filterOn: Boolean) {
        rocketsMutableLive.postValue(filterResults(rockets, filterOn))
    }

    private fun filterResults(rockets: List<RocketEntity>, filterOn: Boolean):
            List<RocketEntity> =
        if (filterOn) rockets.filter { rocket -> rocket.active }.toMutableList()
        else rockets

    private fun getDataFromRepo(forceRefresh: Boolean) =
        rocketRepo.getRockets(forceRefresh) {
            rockets = it
            updateLiveData(rockets, filterOn)
        }
}
