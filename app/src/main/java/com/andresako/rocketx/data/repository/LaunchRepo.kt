package com.andresako.rocketx.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andresako.rocketx.R
import com.andresako.rocketx.data.NetworkStatus
import com.andresako.rocketx.data.RocketApiService
import com.andresako.rocketx.data.response.LaunchDTO
import com.andresako.rocketx.data.room.dao.LaunchDAO
import com.andresako.rocketx.data.room.entity.LaunchEntity
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers

interface LaunchRepoInterface {

    fun clear()

    fun getLaunches(
        rocketId: String,
        refresh: Boolean,
        callback: (launchList: List<LaunchEntity>) -> Unit
    )

    fun getNetworkStatusData(): LiveData<NetworkStatus>
}

class LaunchRepo(
    private val rocketXApi: RocketApiService,
    private val launchDAO: LaunchDAO
) : LaunchRepoInterface {

    private val disposables = CompositeDisposable()
    private val networkStatusLive = MutableLiveData<NetworkStatus>()

    override fun clear() {
        disposables.clear()
    }

    override fun getLaunches(
        rocketId: String,
        refresh: Boolean,
        callback: (launchList: List<LaunchEntity>) -> Unit
    ) {
        networkStatusLive.postValue(NetworkStatus.RUNNING)
        if (refresh) getOnlineLaunches(rocketId, callback)
        else getStoredLaunches(rocketId, callback)
    }

    override fun getNetworkStatusData(): LiveData<NetworkStatus> {
        return networkStatusLive
    }

    private fun getOnlineLaunches(
        rocketId: String,
        callback: (launchList: List<LaunchEntity>) -> Unit
    ) {
        disposables.add(
            rocketXApi.getLaunches()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> onSuccessOnline(rocketId, response, callback) },
                    { error -> onResponseError(error) })
        )
    }

    private fun getStoredLaunches(
        rocketId: String,
        callback: (launchList: List<LaunchEntity>) -> Unit
    ) {
        disposables.add(
            launchDAO.getLaunchesForRocketId(rocketId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> onSuccessStored(rocketId, response, callback) },
                    { error -> onResponseError(error) })
        )
    }

    private fun onSuccessOnline(
        rocketId: String,
        response: List<LaunchDTO>,
        callback: (launches: List<LaunchEntity>) -> Unit
    ) {
        val mapper = Mappers.getMapper(LaunchDTOToMap::class.java)
        val launchList = response.map(mapper::map)

        overrideDB(launchList) {
            callback(filterResponse(rocketId, launchList))
            networkStatusLive.postValue(NetworkStatus.DONE)
        }
    }

    private fun onSuccessStored(
        rocketId: String,
        response: List<LaunchEntity>,
        callback: (launchList: List<LaunchEntity>) -> Unit
    ) {
        if (response.isNotEmpty()) {
            callback(response)
            networkStatusLive.postValue(NetworkStatus.DONE)
        } else
            getOnlineLaunches(rocketId, callback)
    }

    private fun onResponseError(error: Throwable) {
        error.printStackTrace()
        networkStatusLive.postValue(NetworkStatus.error(R.string.error))
    }

    private fun overrideDB(
        launchList: List<LaunchEntity>,
        onSaved: () -> Unit
    ) {
        Completable.fromAction { launchDAO.saveAll(launchList) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() = onSaved()

                override fun onError(error: Throwable) = onResponseError(error)
            })
    }

    private fun filterResponse(
        rocketId: String,
        launchList: List<LaunchEntity>
    ): List<LaunchEntity> {
        return launchList.filter { launch -> launch.rocketId == rocketId }
    }
}

@Mapper
interface LaunchDTOToMap {

    @Mappings(
        Mapping(source = "rocket.rocketId", target = "rocketId"),
        Mapping(source = "rocket.rocketName", target = "rocketName"),
        Mapping(source = "links.missionPatchSmall", target = "missionPatchSmall")
    )
    fun map(dto: LaunchDTO): LaunchEntity
}