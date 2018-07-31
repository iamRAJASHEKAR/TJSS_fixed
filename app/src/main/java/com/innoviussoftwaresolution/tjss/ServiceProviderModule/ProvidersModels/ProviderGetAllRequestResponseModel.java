package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProviderGetAllRequestResponseModel {

    @SerializedName("message")
    private List<RequestModel> requestList;

    public ProviderGetAllRequestResponseModel() {
    }

    public ProviderGetAllRequestResponseModel(ArrayList<RequestModel> requestList) {
        this.requestList = requestList;
    }

    public List<RequestModel> getRequestList() {
        return requestList;
    }

    public void setRequestList(ArrayList<RequestModel> requestList) {
        this.requestList = requestList;
    }
}
