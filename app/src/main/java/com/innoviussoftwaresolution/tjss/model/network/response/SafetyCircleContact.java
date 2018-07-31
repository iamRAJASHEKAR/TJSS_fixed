package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 22-09-17.
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
        "id",
        "name",
        "phone",
        "userId",
        "email"
})
public class SafetyCircleContact implements Parcelable {

    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("userId")
    public String userId;

    @JsonProperty("email")
    public String email;


    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public SafetyCircleContact() {
    }

    @JsonIgnore
    protected SafetyCircleContact(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        userId = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(userId);
        dest.writeString(email);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SafetyCircleContact> CREATOR = new Creator<SafetyCircleContact>() {
        @Override
        public SafetyCircleContact createFromParcel(Parcel in) {
            return new SafetyCircleContact(in);
        }

        @Override
        public SafetyCircleContact[] newArray(int size) {
            return new SafetyCircleContact[size];
        }
    };

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}