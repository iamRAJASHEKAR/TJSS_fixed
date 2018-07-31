package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

public class GetCategoryRequestModel {

    @SerializedName("userId")
    private String userId;

    public GetCategoryRequestModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
