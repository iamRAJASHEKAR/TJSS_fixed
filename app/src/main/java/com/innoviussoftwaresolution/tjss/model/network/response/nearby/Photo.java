package com.innoviussoftwaresolution.tjss.model.network.response.nearby;

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
        "height",
        "html_attributions",
        "photo_reference",
        "width"
})
public class Photo {

    @JsonProperty("height")
    public long height;
    @JsonProperty("html_attributions")
    public List<Object> htmlAttributions = null;
    @JsonProperty("photo_reference")
    public String photoReference;
    @JsonProperty("width")
    public long width;
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