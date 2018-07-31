package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 16-09-17.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.android.gms.maps.model.LatLng;
import com.innoviussoftwaresolution.tjss.model.internal.ClusterItemBase;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "provide_name",
        "provider_lat",
        "provider_long",
        "provider_address",
        "provider_phone"
})
public class ServiceProvider implements ClusterItemBase{

    @JsonProperty("id")
    public String id;
    @JsonProperty("provide_name")
    public String provideName;
    @JsonProperty("provider_lat")
    public String providerLat;
    @JsonProperty("provider_long")
    public String providerLong;
    @JsonProperty("provider_address")
    public String providerAddress;
    @JsonProperty("provider_phone")
    public String providerPhone;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private LatLng mPosition;

    @JsonIgnore
    private int zIndex = 0;

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
            mPosition = new LatLng(Double.parseDouble(providerLat),Double.parseDouble(providerLong));
        return mPosition;
    }

    @Override
    public String getTitle() {
        return provideName;
    }

    @Override
    public String getSnippet() {
        return providerAddress;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String[] createParts() {
        return new String[0];
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