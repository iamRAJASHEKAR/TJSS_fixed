package com.twixttechnologies.tjss.model.network.response;

/**
 * @author Sony Raj on 02-11-17.
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
        "alertId",
        "displayName",
        "status",
        "providerName",
        "created_at"
})
public class HelpAlert implements Parcelable {

    public static final Creator<HelpAlert> CREATOR = new Creator<HelpAlert>() {
        @Override
        public HelpAlert createFromParcel(Parcel in) {
            return new HelpAlert(in);
        }

        @Override
        public HelpAlert[] newArray(int size) {
            return new HelpAlert[size];
        }
    };
    @JsonProperty("alertId")
    public Integer alertId;
    @JsonProperty("displayName")
    public String displayName;
    @JsonProperty("status")
    public Integer status;
    @JsonProperty("providerName")
    public String providerName;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public HelpAlert() {
    }

    @JsonIgnore
    protected HelpAlert(Parcel in) {
        if (in.readByte() == 0) {
            alertId = null;
        } else {
            alertId = in.readInt();
        }
        displayName = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        providerName = in.readString();
        createdAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (alertId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(alertId);
        }
        dest.writeString(displayName);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeString(providerName);
        dest.writeString(createdAt);
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