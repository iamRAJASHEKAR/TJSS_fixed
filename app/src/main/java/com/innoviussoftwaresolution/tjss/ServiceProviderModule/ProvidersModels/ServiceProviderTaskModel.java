package com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels;

public class ServiceProviderTaskModel {

    private String task;
    private String status;

    public ServiceProviderTaskModel() {
    }

    public ServiceProviderTaskModel(String task, String status) {
        this.task = task;
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
