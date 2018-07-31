package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

public class CheckStatusResponseModel {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public CheckStatusResponseModel() {
    }

    public CheckStatusResponseModel(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

