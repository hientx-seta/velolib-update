package com.jcdecaux.velolib.view.interfaces

import com.jcdecaux.velolib.model.StationItem

/**
 * Created by hientx on 11/16/2017.
 */

interface StationDetailView {

    fun onStationDetailDisplayed(stationItem: StationItem)

    fun onError(throwable: Throwable?)

    fun hideLoading()

}