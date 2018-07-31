package com.innoviussoftwaresolution.tjss.model.network.response;

/**
 * @author Sony Raj on 31-10-17.
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
        "incident_title",
        "incident_lat",
        "incident_long",
        "incident_description",
        "incident_time",
        "incidentType",
        "incidentId",
        "log"
})
public class IncidentDetail
{

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
    public Integer incidentId;
    @JsonProperty("log")
    public List<Log> log = null;
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