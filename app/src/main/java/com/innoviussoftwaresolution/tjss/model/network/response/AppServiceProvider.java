package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 25-09-17.
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
        "provide_name",
        "description",
        "category",
        "subCategory",
        "phone",
        "latitude",
        "longitude",
        "deleteId"
})
public class AppServiceProvider implements Parcelable{

    @JsonProperty("id")
    public String id;
    @JsonProperty("provide_name")
    public String provideName;
    @JsonProperty("description")
    public String description;
    @JsonProperty("category")
    public String category;
    @JsonProperty("subCategory")
    public String subCategory;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("latitude")
    public double latitude;
    @JsonProperty("longitude")
    public double longitude;
    @JsonProperty("deleteId")
    public int deleteId;

    @JsonIgnore
    public boolean isSelected = false;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public AppServiceProvider() {
    }

    @JsonIgnore
    protected AppServiceProvider(Parcel in) {
        id = in.readString();
        provideName = in.readString();
        description = in.readString();
        category = in.readString();
        subCategory = in.readString();
        phone = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(provideName);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(subCategory);
        dest.writeString(phone);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppServiceProvider> CREATOR = new Creator<AppServiceProvider>() {
        @Override
        public AppServiceProvider createFromParcel(Parcel in) {
            return new AppServiceProvider(in);
        }

        @Override
        public AppServiceProvider[] newArray(int size) {
            return new AppServiceProvider[size];
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