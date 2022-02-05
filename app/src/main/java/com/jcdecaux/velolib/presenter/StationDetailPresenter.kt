package com.jcdecaux.velolib.presenter

import com.jcdecaux.velolib.model.StationItem
import com.jcdecaux.velolib.view.interfaces.StationDetailView
import rx.Subscription

class StationDetailPresenter {

    private var stationDetailView: StationDetailView? = null

    private var subscription: Subscription? = null

    fun onViewCreated(detailView: StationDetailView) {
        stationDetailView = detailView
    }


    fun loadStationDetail(stationItem: StationItem) {
        stationDetailView?.onStationDetailDisplayed(stationItem)
    }

    fun onDestroy() {
        subscription?.unsubscribe()
    }

}