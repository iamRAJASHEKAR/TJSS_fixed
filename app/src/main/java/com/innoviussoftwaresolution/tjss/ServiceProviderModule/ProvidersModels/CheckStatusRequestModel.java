package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

public class CheckStatusRequestModel {

    @SerializedName("provider_id")
    private String providerId;

    @SerializedName("user_id")
    private String userId;

    public CheckStatusRequestModel() {
    }

    public CheckStatusRequestModel(String providerId, String userId) {
        this.providerId = providerId;
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
