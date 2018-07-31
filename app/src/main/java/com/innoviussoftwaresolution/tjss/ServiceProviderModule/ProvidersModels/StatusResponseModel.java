package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

public class StatusResponseModel {

    @SerializedName("status")
    private  String status;

    public StatusResponseModel() {
    }

    public StatusResponseModel(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
