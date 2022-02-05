package com.jcdecaux.velolib.model

/**
 * Created by hientx on 11/16/2017.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.realm.RealmObject
import org.parceler.Parcel

@Parcel(value = Parcel.Serialization.BEAN, analyze = arrayOf(Position::class))
open class Position : RealmObject() {

    @SerializedName("lat")
    @Expose
    var lat: String? = null

    @SerializedName("lng")
    @Expose
    var lng: String? = null

}