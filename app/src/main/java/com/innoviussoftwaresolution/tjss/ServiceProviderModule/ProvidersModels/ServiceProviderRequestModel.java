package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

public class ServiceProviderRequestModel {

    private String requestTitle;
    private String requestDescription;

    public ServiceProviderRequestModel(String requestTitle, String requestDescription) {
        this.requestTitle = requestTitle;
        this.requestDescription = requestDescription;
    }

    public ServiceProviderRequestModel() {
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }
}
