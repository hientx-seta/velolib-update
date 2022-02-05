package com.jcdecaux.velolib.presenter

/**
 * Created by hientx on 11/16/2017.
 */

import com.jcdecaux.velolib.api.StationApiInterface
import com.jcdecaux.velolib.config.Constants
import com.jcdecaux.velolib.model.StationItem
import com.jcdecaux.velolib.view.interfaces.StationView
import io.realm.Realm
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class StationPresenter {

    private var stationApiInterface: StationApiInterface? = null
    private var stationView: StationView? = null

    private var subscription: Subscription? = null

    fun onViewCreated(view: StationView) {
        stationView = view
    }

    fun setStationApiInterface(stationApiInterface1: StationApiInterface) {
        this.stationApiInterface = stationApiInterface1
    }

    fun loadStation() {
        subscription = stationApiInterface!!
                .getStations(Constants.CONTRACT, Constants.API_KEY)
                .flatMap({ items ->
                    Realm.getDefaultInstance().executeTransaction({ realm ->
                        realm.delete(StationItem::class.java)
                        realm.insert(items)
                    })
                    Observable.just(items)
                })
                .onErrorResumeNext { throwable ->
                    val realm = Realm.getDefaultInstance()
                    val items = realm.where(StationItem::class.java).findAll()
                    Observable.just(realm.copyFromRealm(items))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { stationView?.hideLoading() }
                .subscribe({ stationView?.onStationItemLoaded(it) }, { stationView?.onError(it) })
    }

    fun onDestroy() {
        subscription?.unsubscribe()
    }

}