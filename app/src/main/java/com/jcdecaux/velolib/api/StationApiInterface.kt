package com.jcdecaux.velolib.api

import com.jcdecaux.velolib.model.StationItem
import retrofit2.http.GET
import rx.Observable

/**
 * Created by hientx on 11/16/2017.
 */

interface StationApiInterface {

    @GET("stations")//?contract=Paris&apiKey=b2bfb1bc9c9b23a193e4230ab62db2cd5c7185de
    fun getStations(@retrofit2.http.Query("contract") contract: String,
                    @retrofit2.http.Query("apiKey") apiKey: String): Observable<List<StationItem>>

}
