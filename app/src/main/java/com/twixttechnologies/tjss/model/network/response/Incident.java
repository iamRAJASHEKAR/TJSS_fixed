package com.twixttechnologies.tjss.model.network.response;

/**
 * @author Sony Raj on 22-08-17.
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
        "incident_title",
        "incident_lat",
        "incident_long",
        "incident_description",
        "incident_time",
        "incidentType",
        "incidentId"
})
public class Incident implements Parcelable, ClusterItemBase {

    public static final Creator<Incident> CREATOR = new Creator<Incident>() {
        @Override
        public Incident createFromParcel(Parcel in) {
            return new Incident(in);
        }

        @Override
        public Incident[] newArray(int size) {
            return new Incident[size];
        }
    };
    @JsonProperty("incident_title")
    public String incidentTitle;
    @JsonProperty("incident_lat")
    public double incidentLat;
    @JsonProperty("incident_long")
    public double incidentLong;
    @JsonProperty("incident_description")
    public String incidentDescription;
    @JsonProperty("incident_time")
    public String incidentTime;
    @JsonProperty("incidentType")
    public String incidentType;
    @JsonProperty("incidentId")
    public String incidentId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private LatLng mPosition;

    @JsonIgnore
    private String[] mParts;


    @JsonIgnore
    private int zIndex = 0;

    public Incident() {
    }

    @JsonIgnore
    protected Incident(Parcel in) {
        incidentTitle = in.readString();
        incidentLat = in.readDouble();
        incidentLong = in.readDouble();
        incidentDescription = in.readString();
        incidentTime = in.readString();
        incidentType = in.readString();
        incidentId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(incidentTitle);
        dest.writeDouble(incidentLat);
        dest.writeDouble(incidentLong);
        dest.writeString(incidentDescription);
        dest.writeString(incidentTime);
        dest.writeString(incidentType);
        dest.writeString(incidentId);
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
            mPosition = new LatLng(incidentLat, incidentLong);
        return mPosition;
    }

    @Override
    public String getTitle() {
        return incidentTitle;
    }

    @Override
    public String getSnippet() {
        return incidentDescription;
    }

    @Override
    public String getId() {
        return incidentId;
    }

    @Override
    public String[] createParts() {
        if (mParts == null)
            mParts = new String[]{incidentTitle, incidentDescription, incidentTime, incidentId};
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