package com.andresako.rocketx.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andresako.rocketx.R
import com.andresako.rocketx.data.NetworkStatus
import com.andresako.rocketx.data.RocketApiService
import com.andresako.rocketx.data.response.RocketDTO
import com.andresako.rocketx.data.room.dao.RocketDAO
import com.andresako.rocketx.data.room.entity.RocketEntity
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

interface RocketRepoInterface {

    fun clear()

    fun getRockets(
        refresh: Boolean,
        callback: (rocketList: List<RocketEntity>) -> Unit
    )

    fun getNetworkStatusData(): LiveData<NetworkStatus>
}

class RocketRepo(
    private val rocketXApi: RocketApiService,
    private val rocketDAO: RocketDAO
) : RocketRepoInterface {

    private val disposables = CompositeDisposable()
    private val networkStatusLive = MutableLiveData<NetworkStatus>()

    override fun clear() {
        disposables.clear()
    }

    override fun getRockets(refresh: Boolean, callback: (rocketList: List<RocketEntity>) -> Unit) {
        networkStatusLive.postValue(NetworkStatus.RUNNING)
        if (refresh) getOnlineRockets(callback)
        else getStoredRockets(callback)

    }

    override fun getNetworkStatusData(): LiveData<NetworkStatus> {
        return networkStatusLive
    }

    private fun getOnlineRockets(callback: (rocketList: List<RocketEntity>) -> Unit) {
        disposables.add(
            rocketXApi.getRockets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> onSuccessOnline(response, callback) },
                    { error -> onResponseError(error) })
        )
    }

    private fun getStoredRockets(callback: (rocketList: List<RocketEntity>) -> Unit) {
        disposables.add(
            rocketDAO.getRockets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response -> onSuccessStored(response, callback) },
                    { error -> onResponseError(error) }
                )
        )
    }

    private fun onSuccessOnline(
        response: List<RocketDTO>,
        callback: (rockets: List<RocketEntity>) -> Unit
    ) {
        val map = Mappers.getMapper(RocketDTOToMap::class.java)
        val rocketList = response.map(map::map)
        overrideDB(rocketList) {
            callback(rocketList)
            networkStatusLive.postValue(NetworkStatus.DONE)
        }

    }

    private fun overrideDB(
        rocketList: List<RocketEntity>,
        onSaved: () -> Unit
    ) {
        Completable.fromAction { rocketDAO.saveAll(rocketList) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}

                override fun onComplete() = onSaved()

                override fun onError(error: Throwable) = onResponseError(error)
            })
    }

    private fun onSuccessStored(
        response: List<RocketEntity>,
        callback: (rockets: List<RocketEntity>) -> Unit
    ) {
        if (response.isNotEmpty()) {
            callback(response)
            networkStatusLive.postValue(NetworkStatus.DONE)
        } else
            getOnlineRockets(callback)
    }

    fun onResponseError(error: Throwable) {
        error.printStackTrace()
        networkStatusLive.postValue(NetworkStatus.error(R.string.error))
    }

}

@Mapper
interface RocketDTOToMap {

    @Mapping(target = "numberOfEngines", source = "engines.number")
    fun map(dto: RocketDTO): RocketEntity
}