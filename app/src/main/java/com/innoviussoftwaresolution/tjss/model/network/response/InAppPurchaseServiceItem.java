package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 09-10-17.
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
        "id",
        "name",
        "description",
        "amount",
        "points",
        "providerName",
        "phone",
        "address",
        "providerId"
})
public class InAppPurchaseServiceItem {

    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("amount")
    public String amount;
    @JsonProperty("points")
    public String points;
    @JsonProperty("providerName")
    public String providerName;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("address")
    public String address;
    @JsonProperty("providerId")
    public String providerId;

    @JsonIgnore
    public boolean isOpen = false;

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