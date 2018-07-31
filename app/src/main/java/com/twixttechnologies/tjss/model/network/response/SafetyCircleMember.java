package com.twixttechnologies.tjss.model.network.response;

/**
 * @author Sony Raj on 21-08-17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.android.gms.maps.model.LatLng;
import com.twixttechnologies.tjss.model.internal.ClusterItemBase;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "battery",
        "userId",
        "circlemembers",
        "admins",
        "latitude",
        "longitude",
        "fname",
        "circleId",
        "image",
        "phone",
        "latitude",
        "longitude"
})
public class SafetyCircleMember implements Parcelable ,ClusterItemBase{

    public static final Creator<SafetyCircleMember> CREATOR = new Creator<SafetyCircleMember>() {
        @Override
        public SafetyCircleMember createFromParcel(Parcel in) {
            return new SafetyCircleMember(in);
        }

        @Override
        public SafetyCircleMember[] newArray(int size) {
            return new SafetyCircleMember[size];
        }
    };
    @JsonProperty("battery")
    public String battery;
    @JsonProperty("userId")
    public String userId;
    @JsonProperty("circlemembers")
    public String circlemembers;
    @JsonProperty("admins")
    public int admin;
    @JsonProperty("latitude")
    public double latitude;
    @JsonProperty("longitude")
    public double longitude;
    @JsonProperty("fname")
    public String fname;
    @JsonProperty("circleId")
    public String circleId;
    @JsonProperty("image")
    public String image;
    @JsonProperty("phone")
    public String phone;
    @JsonIgnore
    public boolean isSelected = false;
    @JsonIgnore
    public String locationName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private LatLng mPosition;

    @JsonIgnore
    private String[] mParts;

    @JsonIgnore
    private int zIndex = 0;

    @JsonIgnore
    protected SafetyCircleMember(Parcel in) {
        battery = in.readString();
        userId = in.readString();
        circlemembers = in.readString();
        admin = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        fname = in.readString();
        circleId = in.readString();
        image = in.readString();
        phone = in.readString();
        isSelected = in.readByte() != 0;
    }

    public SafetyCircleMember() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(battery);
        dest.writeString(userId);
        dest.writeString(circlemembers);
        dest.writeInt(admin);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(fname);
        dest.writeString(circleId);
        dest.writeString(image);
        dest.writeString(phone);
        dest.writeByte((byte) (isSelected ? 1 : 0));
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

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public LatLng getPosition() {
        if (mPosition == null)
            mPosition = new LatLng(latitude,longitude);
        return mPosition;
    }

    @Override
    public String getTitle() {
        return fname;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    @Override
    public String getId() {
        return userId;
    }

    @Override
    public String[] createParts() {
        if (mParts == null)
            mParts = new String[]{fname,battery,image,phone,userId,"safety"};
        return mParts;
    }


    @Override
    public int zIndex() {
        return zIndex;
    }


    @Override
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

}