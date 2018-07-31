package com.innoviussoftwaresolution.tjss.model.network.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sony Raj on 07-10-17.
 */

public class UserTimeLineItem {

    @JsonProperty("placeName")
    public String placeName;
    @JsonProperty("time")
    public String time;
    @JsonProperty("startedTime")
    public String startedTime;
    @JsonProperty("stoppedTime")
    public String stoppedTime;
    @JsonProperty("route")
    public String route;
    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public String id;
    @JsonProperty("userId")
    public String userId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}