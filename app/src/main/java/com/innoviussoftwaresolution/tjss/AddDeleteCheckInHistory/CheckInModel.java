package com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 3/29/2018.
 */

public class CheckInModel {

    @SerializedName("userId")
    private String placeId;

    @SerializedName("lat")
    private String lat;

    @SerializedName("long")
    private String longt;

    @SerializedName("locName")
    private String locName;


    public CheckInModel() {
    }

    public CheckInModel(String userId, String lat, String longt, String locName, String image) {
        this.placeId = userId;
        this.lat = lat;
        this.longt = longt;
        this.locName = locName;

    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }



}
