package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

public class ProviderList {

    @SerializedName("provider_id")
    String provider_id;

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }
}
