package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProviderGetAllRequestsRequestModel {

    @SerializedName("provider_id")
    private String providerId;

    public ProviderGetAllRequestsRequestModel(String providerId) {
        this.providerId = providerId;
    }

    public ProviderGetAllRequestsRequestModel() {
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
