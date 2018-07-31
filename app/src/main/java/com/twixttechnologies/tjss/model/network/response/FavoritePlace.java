package com.twixttechnologies.tjss.model.network.response;

/**
 * @author Sony Raj on 07-08-17.
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
        "name",
        "address",
        "favorite_lat",
        "favorite_long",
        "placeId",
        "favoriteId",
        "radius"
})
public class FavoritePlace implements Parcelable , ClusterItemBase {

    public static final Creator<FavoritePlace> CREATOR = new Creator<FavoritePlace>() {
        @Override
        public FavoritePlace createFromParcel(Parcel in) {
            return new FavoritePlace(in);
        }

        @Override
        public FavoritePlace[] newArray(int size) {
            return new FavoritePlace[size];
        }
    };
    @JsonProperty("name")
    public String name;
    @JsonProperty("address")
    public String address;
    @JsonProperty("favorite_lat")
    public String favoriteLat;
    @JsonProperty("favorite_long")
    public String favoriteLong;
    @JsonProperty("placeId")
    public String placeId;
    @JsonProperty("radius")
    public int radius;
    @JsonProperty("favoriteId")
    public String favPlaceId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private LatLng mPosition;

    @JsonIgnore
    private String[] mParts;

    @JsonIgnore
    private int zIndex = 0;

    public FavoritePlace() {
    }

    @JsonIgnore
    protected FavoritePlace(Parcel in) {
        name = in.readString();
        address = in.readString();
        favoriteLat = in.readString();
        favoriteLong = in.readString();
        placeId = in.readString();
        radius = in.readInt();
        favPlaceId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(favoriteLat);
        dest.writeString(favoriteLong);
        dest.writeString(placeId);
        dest.writeInt(radius);
        dest.writeString(favPlaceId);
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
        return null;
    }

    @Override
    public LatLng getPosition() {
        if (mPosition == null)
            mPosition = new LatLng(Double.parseDouble(favoriteLat),Double.parseDouble(favoriteLong));
        return mPosition;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    @Override
    public String getId() {
        return favPlaceId;
    }

    @Override
    public String[] createParts() {
        if (mParts == null)
            mParts = new String[]{name,address,placeId};
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