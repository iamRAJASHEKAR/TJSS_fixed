package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

import com.google.gson.annotations.SerializedName;

public class AcceptRequestModel {

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("request_status")
    private  String requestStatus;

    public AcceptRequestModel() {
    }

    public AcceptRequestModel(String requestId, String requestStatus) {
        this.requestId = requestId;
        this.requestStatus = requestStatus;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
