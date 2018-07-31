package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

public class NearByServiceProviderRequestModel {

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("userId")
    private String userId;

    public NearByServiceProviderRequestModel() {
    }

    public NearByServiceProviderRequestModel(String latitude, String longitude, String userId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
