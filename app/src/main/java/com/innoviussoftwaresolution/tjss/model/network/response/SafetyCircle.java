package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 08-08-17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "circleName",
        "circleId",
        "admins",
        "image",
        "alertOptions"
})
public class SafetyCircle implements Parcelable {

    public static final Creator<SafetyCircle> CREATOR = new Creator<SafetyCircle>() {
        @Override
        public SafetyCircle createFromParcel(Parcel in) {
            return new SafetyCircle(in);
        }

        @Override
        public SafetyCircle[] newArray(int size) {
            return new SafetyCircle[size];
        }
    };
    @JsonProperty("circleName")
    public String circleName;
    @JsonProperty("circleId")
    public String circleId;
    @JsonProperty("admins")
    public String admins;
    @JsonProperty("image")
    public String image;
    @JsonProperty("alertOptions")
    public String alertOptions;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    public SafetyCircle(Parcel in) {
        circleName = in.readString();
        circleId = in.readString();
        admins = in.readString();
        image = in.readString();
        alertOptions = in.readString();
    }

    public SafetyCircle() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(circleName);
        dest.writeString(circleId);
        dest.writeString(admins);
        dest.writeString(image);
        dest.writeString(alertOptions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
