package com.twixttechnologies.tjss.model.network.response;

/**
 * @author Sony Raj on 05-08-17.
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
        "plan_name",
        "plan_description",
        "plan_price",
        "stripePlan"
})
public class Plan {


    @JsonProperty("id")
    public String id;
    @JsonProperty("plan_name")
    public String planName;
    @JsonProperty("plan_description")
    public String planDescription;
    @JsonProperty("plan_price")
    public String planPrice;
    @JsonProperty("stripePlan")
    public String stripePlan;
    @JsonIgnore
    public boolean isSelected = false;
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