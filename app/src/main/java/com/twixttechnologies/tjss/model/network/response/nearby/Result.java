package com.twixttechnologies.tjss.model.network.response.nearby;

/**
 * @author Sony Raj on 15-09-17.
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
        "geometry",
        "icon",
        "id",
        "name",
        "opening_hours",
        "photos",
        "place_id",
        "scope",
        "alt_ids",
        "reference",
        "types",
        "vicinity"
})
public class Result {

    @JsonProperty("geometry")
    public Geometry geometry;
    @JsonProperty("icon")
    public String icon;
    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("opening_hours")
    public OpeningHours openingHours;
    @JsonProperty("photos")
    public List<Photo> photos = null;
    @JsonProperty("place_id")
    public String placeId;
    @JsonProperty("scope")
    public String scope;
    @JsonProperty("alt_ids")
    public List<AltId> altIds = null;
    @JsonProperty("reference")
    public String reference;
    @JsonProperty("types")
    public List<String> types = null;
    @JsonProperty("vicinity")
    public String vicinity;
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
