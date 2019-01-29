package com.andresako.rocketx.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andresako.rocketx.data.NetworkStatus
import com.andresako.rocketx.data.repository.LaunchRepo
import com.andresako.rocketx.data.room.entity.LaunchEntity

interface LaunchDetailsViewModelInterface {

    fun getLaunches(): MutableLiveData<List<LaunchEntity>>

    fun loadLaunches(rocketId: String, refresh: Boolean)

    fun getNetworkStatus(): LiveData<NetworkStatus>

}

class LaunchDetailsViewModel(
    val launchRepo: LaunchRepo
) : ViewModel(), LaunchDetailsViewModelInterface {

    val launchesMutableLive = MutableLiveData<List<LaunchEntity>>()
    private val networkStatus = launchRepo.getNetworkStatusData()

    override fun getLaunches(): MutableLiveData<List<LaunchEntity>> {
        return launchesMutableLive
    }

    override fun loadLaunches(
        rocketId: String,
        refresh: Boolean
    ) {
        launchRepo.getLaunches(rocketId, refresh) {
            launchesMutableLive.postValue(it)
        }
    }

    override fun getNetworkStatus(): LiveData<NetworkStatus> {
        return networkStatus
    }

}
