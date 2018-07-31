package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearByServiceProviderResponseModel {


    @SerializedName("selected_service_provider")
    private List<ProviderList> selectedProviderList;

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public List<ProviderList> getSelectedProviderList() {
        return selectedProviderList;
    }

    public void setSelectedProviderList(List<ProviderList> selectedProviderList) {
        this.selectedProviderList = selectedProviderList;
    }

    public void setStatus(String status) {
        this.status = status;
    }


//    private class ProviderList {
//        @SerializedName("provider_id")
//        String provider_id;
//
//        public String getProvider_id() {
//            return provider_id;
//        }
//
//        public void setProvider_id(String provider_id) {
//            this.provider_id = provider_id;
//        }
//    }
}
