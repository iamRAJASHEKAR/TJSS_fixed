package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 09-08-17.
 */

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
        "inviteCode",
        "status",
        "circleId",
        "primary"
})
public class CreateSafetyCircleResponse {

    @JsonProperty("inviteCode")
    public String inviteCode;
    @JsonProperty("status")
    public String status;
    @JsonProperty("circleId")
    public String circleId;
    @JsonProperty("primary")
    public String primary;
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