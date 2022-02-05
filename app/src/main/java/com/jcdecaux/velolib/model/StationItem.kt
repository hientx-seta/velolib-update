package com.jcdecaux.velolib.model

/**
 * Created by hientx on 11/16/2017.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import org.parceler.Parcel

@Parcel(value = Parcel.Serialization.BEAN, analyze = arrayOf(StationItem::class))
open class StationItem : RealmObject() {

    @SerializedName("position")
    @Expose
    var position: Position? = null

    @SerializedName("number")
    @Expose
    var number: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("banking")
    @Expose
    var banking: Boolean? = null

    @SerializedName("bonus")
    @Expose
    var bonus: Boolean? = null

    @SerializedName("contract_name")
    @Expose
    var contractName: String? = null

    @SerializedName("bike_stands")
    @Expose
    var bikeStands: Int? = null

    @SerializedName("available_bike_stands")
    @Expose
    var availableBikeStands: Int? = null

    @SerializedName("available_bikes")
    @Expose
    var availableBikes: Int? = null

    @SerializedName("last_update")
    @Expose
    var lastUpdate: Long? = null

}