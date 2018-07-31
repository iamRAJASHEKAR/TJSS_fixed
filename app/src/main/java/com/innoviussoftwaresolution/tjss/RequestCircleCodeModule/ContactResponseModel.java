package com.innoviussoftwaresolution.tjss.RequestCircleCodeModule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactResponseModel {

    @SerializedName("message")
    List<CircleDetailsModel> circleDetails;

    public List<CircleDetailsModel> getCircleDetails() {
        return circleDetails;
    }

    public void setCircleDetails(List<CircleDetailsModel> circleDetails) {
        this.circleDetails = circleDetails;
    }

    public ContactResponseModel() {
    }
}
