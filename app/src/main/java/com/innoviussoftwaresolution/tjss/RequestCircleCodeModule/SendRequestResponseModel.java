package com.innoviussoftwaresolution.tjss.RequestCircleCodeModule;

import com.google.gson.annotations.SerializedName;

public class SendRequestResponseModel {

    @SerializedName("success")
    private String message;

    public SendRequestResponseModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
