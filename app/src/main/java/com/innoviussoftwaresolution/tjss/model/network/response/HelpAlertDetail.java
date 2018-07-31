package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 02-11-17.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "displayName",
        "alert_status",
        "provide_name",
        "created_at",
        "remarks",
        "log"
})
public class HelpAlertDetail {

    @JsonProperty("displayName")
    public String displayName;
    @JsonProperty("alert_status")
    public Integer alertStatus;
    @JsonProperty("provide_name")
    public String provideName;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("remarks")
    public String remarks;
    @JsonProperty("log")
    public List<HelpAlertLog> log = null;
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