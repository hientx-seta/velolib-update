package com.jcdecaux.velolib.model;

/**
 * Created by hientx on 11/16/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StationResponse {

    @SerializedName("")
    @Expose
    private List<StationItem> stationItem = new ArrayList<StationItem>();

    public List<StationItem> getStationItem() {
        return stationItem;
    }

    public void setStationItem(List<StationItem> stationItem) {
        this.stationItem = stationItem;
    }

}